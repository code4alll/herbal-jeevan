package com.ecommerce.HerbalJeevan.Payment;

public class TransactionDetails {
	
	private String orderId;
	private String currency;
	private Integer amount;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public TransactionDetails(String orderId, String currency, Integer amount) {
		super();
		this.orderId = orderId;
		this.currency = currency;
		this.amount = amount;
	}
	public TransactionDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public TransactionDetails(String url,String orderId) {
		super();
		this.url = url;
		this.orderId=orderId;
		
	}
	public TransactionDetails(String orderId, String currency, Integer amount, String url) {
		super();
		this.orderId = orderId;
		this.currency = currency;
		this.amount = amount;
		this.url = url;
	}
	
	
	

}
