package com.ecommerce.HerbalJeevan.Payment;

import com.ecommerce.HerbalJeevan.Enums.PaymentStatus;

public class PaymentCallback {
	private String orderId;
    private String paymentId;
    private String payerId;
    public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	private String signature;
    private PaymentStatus paymentStatus;
    private String method;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public PaymentCallback() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	    
	    
	    

}
