package com.ecommerce.HerbalJeevan.Payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.HerbalJeevan.DTO.Response;
import com.ecommerce.HerbalJeevan.Enums.PaymentGatewayType;
import com.ecommerce.HerbalJeevan.Enums.PaymentStatus;
import com.ecommerce.HerbalJeevan.Model.Order;
import com.ecommerce.HerbalJeevan.Model.TransactionDetailModel;
import com.ecommerce.HerbalJeevan.Repository.TransactionDetailRepository;
import com.ecommerce.HerbalJeevan.Service.OrderService;



@Service
public class PaymentService {
    private final OrderService orderService;
    private final Map<PaymentGatewayType, PaymentGateway> paymentGateways;
    private final TransactionDetailRepository transactionRepo;

    @Autowired
    public PaymentService(OrderService orderService, List<PaymentGateway> gateways,TransactionDetailRepository transactionRepo) {
        this.orderService = orderService;
        this.paymentGateways = new HashMap<>();
        this.transactionRepo=transactionRepo;
        for (PaymentGateway gateway : gateways) {
            paymentGateways.put(gateway.getType(), gateway);
        }
    }

    public TransactionDetails createTransaction(PaymentGatewayType gatewayType,String OrderId) throws PaymentException {
        
        
        Order order=orderService.getOrderByOrderId(OrderId);
        
        if(order==null) {
        	throw new PaymentException("No order found with orderId: "+OrderId);
        }
        
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(order.getTotalAmount());
        paymentRequest.setCurrency(order.getCurrency());
        paymentRequest.setDescription(order.getOrderItems().get(0)!=null?order.getOrderItems().get(0).getItemName():"Herbal Jivan product");
       

        PaymentGateway paymentGateway = paymentGateways.get(gatewayType);
        if (paymentGateway == null) {
            throw new IllegalArgumentException("Invalid payment gateway");
        }
        
        TransactionDetails detail=paymentGateway.createTransaction(paymentRequest);
        TransactionDetailModel details2=order.getTransactionDetail();
        details2.setGateway(gatewayType);
        details2.setMode(null);
        details2.setPaymentId(detail.getOrderId());
        details2.setPaymentStatus(PaymentStatus.CREATED);
        details2.setAmount(paymentRequest.getAmount());
        details2.setCurrency(paymentRequest.getCurrency());
        details2.setOrder(order);
        
        order.setTransactionDetail(details2);
        orderService.updateOrder(order);
        
        

        return detail;
    }

    public boolean verifyPaymentSignature(PaymentCallback callback, PaymentGatewayType gatewayType) throws PaymentException {
        PaymentGateway paymentGateway = paymentGateways.get(gatewayType);
        if (paymentGateway == null) {
            throw new IllegalArgumentException("Invalid payment gateway");
        }
        return paymentGateway.verifySignature(callback);
    }

    public String getPaymentStatus(String paymentId, PaymentGatewayType gatewayType) throws PaymentException {
        PaymentGateway paymentGateway = paymentGateways.get(gatewayType);
        if (paymentGateway == null) {
            throw new IllegalArgumentException("Invalid payment gateway");
        }
        return paymentGateway.getPaymentStatus(paymentId);
    }

    public Response<?> handlePaymentCallback(PaymentCallback callback, PaymentGatewayType gatewayType) {
    	 PaymentGateway paymentGateway = paymentGateways.get(gatewayType);
         if (paymentGateway == null) {
             throw new IllegalArgumentException("Invalid payment gateway");
         }
         return paymentGateway.handlePaymentCallback(callback);
    }
}
