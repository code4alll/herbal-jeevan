package com.ecommerce.HerbalJeevan.DTO;

import com.ecommerce.HerbalJeevan.Enums.OrderStage;

public class OrderResponse {
	private String orderId;
	private OrderStage status;
	public OrderResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Double amount;
	private String currency;
	private String currencySymbol;
	
	
	public OrderResponse(String orderId, OrderStage status, Double amount, String currency, String currencySymbol) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.amount = amount;
		this.currency = currency;
		this.currencySymbol = currencySymbol;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencySymbol() {
		return currencySymbol;
	}
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public OrderStage getStatus() {
		return status;
	}
	public void setStatus(OrderStage status) {
		this.status = status;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public OrderResponse(String orderId, OrderStage orderStatus, Double amount) {
		super();
		this.orderId = orderId;
		this.status = orderStatus;
		this.amount = amount;
	}
	
	
	
	

}
