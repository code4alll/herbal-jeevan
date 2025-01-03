package com.ecommerce.HerbalJeevan.Payment;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.HerbalJeevan.Enums.PaymentGatewayType;
import com.ecommerce.HerbalJeevan.Enums.PaymentStatus;
import com.ecommerce.HerbalJeevan.Service.OrderService;
import com.ecommerce.HerbalJeevan.Utility.Response;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class RazorPayGateway implements PaymentGateway {
	
	
	@Autowired
	private OrderService orderService;
	
	@Value("${razorpay.key}")
	private String keys;
	@Value("${razorpay.key.secret}")
	private String keySecret;
	
	public TransactionDetails prepareTransaction(Order order) {
		String orderId=order.get("id");
		String currency=order.get("currency");
		Integer amount=order.get("amount");
		TransactionDetails trans=new TransactionDetails(orderId,currency,amount);
		return trans;
	}
	public TransactionDetails prepareTransaction(PaymentRequest pay) {
		String orderId=null;
		String currency=pay.getCurrency();
		Integer amount=(int) pay.getAmount();
		TransactionDetails trans=new TransactionDetails(orderId,currency,amount);
		return trans;
	}
	
	
	public boolean verifyPaymentSignature(PaymentCallback callback) {
        try {
            String payload = callback.getOrderId() + "|" + callback.getPaymentId();
            String signature = callback.getSignature();
            return Utils.verifySignature(payload, signature,keySecret );
        } catch (RazorpayException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	
	public String getPaymentStatus(String paymentId) throws PaymentException {
		try {
			    RazorpayClient razorpayClient = new RazorpayClient(keys, keySecret);
		        Payment payment = razorpayClient.payments.fetch(paymentId);
		        return payment.get("status");
			
		}catch(RazorpayException e) {
            throw new PaymentException("Failed to create transaction with Razorpay", e);
		}
       
    }

	public String getTransactionId(String paymentId) throws PaymentException {
		try {
		    RazorpayClient razorpayClient = new RazorpayClient(keys, keySecret);
		    Payment payment = razorpayClient.payments.fetch(paymentId);
		    return payment.get("id");
		   
		
	}catch(RazorpayException e) {
        throw new PaymentException("Failed to create transaction with Razorpay", e);
	}
   
}

	@Override
	public TransactionDetails createTransaction(PaymentRequest paymentRequest) throws PaymentException {
		try {
			JSONObject jsonObj=new JSONObject();
			int amountInPaise = (int) Math.round(paymentRequest.getAmount() * 100);
			jsonObj.put("amount",amountInPaise);
			jsonObj.put("currency", paymentRequest.getCurrency());
			RazorpayClient razorpayClient= new RazorpayClient(keys,keySecret);
			Order order=razorpayClient.orders.create(jsonObj);
			return prepareTransaction(order);

//			return prepareTransaction(paymentRequest);
		} catch (Exception e) {
			
			e.printStackTrace();
            throw new PaymentException("Failed to create transaction with Razorpay: "+e.getMessage(), e);
		}
	}


	@Override
	public boolean verifySignature(PaymentCallback callback) throws PaymentException {
		try {
            String payload = callback.getOrderId() + "|" + callback.getPaymentId();
            String signature = callback.getSignature();
            return Utils.verifySignature(payload, signature,keySecret );
        } catch (RazorpayException e) {
            e.printStackTrace();
            throw new PaymentException("Failed to create transaction with Razorpay", e);
        }
	}


	@Override
	public PaymentGatewayType getType() {
		return PaymentGatewayType.RAZORPAY;
	}


	@Override
	public Response<?> handlePaymentCallback(PaymentCallback callback) throws PaymentException {
		
		boolean signature=this.verifySignature(callback);
		if(signature) {
			String status=this.getPaymentStatus(callback.getPaymentId());
			String transactionId=getTransactionId(callback.getPaymentId());
			if(status!=null&&(status.equalsIgnoreCase("Sucess")||status.equalsIgnoreCase("captured"))) {
				 orderService.updateOrderStatus(callback, PaymentStatus.SUCCESS,transactionId);
			}else {
				PaymentStatus s=PaymentStatus.fromString(status);
				if(s.equals(PaymentStatus.UNDEFINED)) {
					orderService.updateOrderStatus(callback, PaymentStatus.FAILED,transactionId);
				}
				orderService.updateOrderStatus(callback, PaymentStatus.fromString(status),transactionId);
			}
			
			
			
			return new Response<>(true,status);
			
		}else {
			throw new PaymentException("Signature not verified");
		}
	}
	
	
	


   

	
	


}
