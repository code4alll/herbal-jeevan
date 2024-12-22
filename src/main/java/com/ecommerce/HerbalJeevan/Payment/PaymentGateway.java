package com.ecommerce.HerbalJeevan.Payment;

import com.ecommerce.HerbalJeevan.DTO.Response;
import com.ecommerce.HerbalJeevan.Enums.PaymentGatewayType;

public interface PaymentGateway {
    TransactionDetails createTransaction(PaymentRequest paymentRequest) throws PaymentException;
    boolean verifySignature(PaymentCallback callback) throws PaymentException;
    String getPaymentStatus(String paymentId) throws PaymentException;
    PaymentGatewayType getType();
    Response<?> handlePaymentCallback(PaymentCallback callback) throws PaymentException;
}
