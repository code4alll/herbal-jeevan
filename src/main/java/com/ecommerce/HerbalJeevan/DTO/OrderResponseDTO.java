package com.ecommerce.HerbalJeevan.DTO;


import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.HerbalJeevan.Enums.OrderStage;
import com.ecommerce.HerbalJeevan.Enums.OrderStatus;



public class OrderResponseDTO {
	private String orderId;
	private OrderStage stage;
	private OrderStatus status;
	private LocalDateTime orderDate;
	private String totalPrice;
	List<OrderItemDto> orderItems;
	
	 private double totalUnitPrice;
	 
	 private double totalSellPrice;
	    
	    
	    private String currency;
	    
	    private String currencySymbol;
	    
	    
	    public double getTotalUnitPrice() {
		return totalUnitPrice;
	}
	public void setTotalUnitPrice(double totalUnitPrice) {
		this.totalUnitPrice = totalUnitPrice;
	}
	public double getTotalSellPrice() {
		return totalSellPrice;
	}
	public void setTotalSellPrice(double totalSellPrice) {
		this.totalSellPrice = totalSellPrice;
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
	
	public OrderStage getStage() {
		return stage;
	}
	public void setStage(OrderStage stage) {
		this.stage = stage;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
//	public OrderResponseDTO(String orderId, String stage, String status, LocalDateTime orderDate, String totalPrice,
//			List<OrderItemDto> orderItems) {
//		super();
//		this.orderId = orderId;
//		this.stage = stage;
//		this.status = status;
//		this.orderDate = orderDate;
//		this.totalPrice = totalPrice;
//		this.orderItems = orderItems;
//	}
}
	
	
	

