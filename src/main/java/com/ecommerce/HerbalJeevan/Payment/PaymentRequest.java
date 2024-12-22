package com.ecommerce.HerbalJeevan.Payment;

import com.ecommerce.HerbalJeevan.Model.Order;

public class PaymentRequest {
	private double amount;
    private String currency;
    private String description;
    private String paymentGatewayType;
    private Order orderId;
    private String successUrl;
    private String cancelUrl;
    
    
    
    
    
	
	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	public Order getOrderId() {
		return orderId;
	}
	public void setOrderId(Order orderId) {
		this.orderId = orderId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPaymentGatewayType() {
		return paymentGatewayType;
	}
	public void setPaymentGatewayType(String paymentGatewayType) {
		this.paymentGatewayType = paymentGatewayType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    

}
