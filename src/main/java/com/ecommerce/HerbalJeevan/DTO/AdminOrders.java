package com.ecommerce.HerbalJeevan.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.HerbalJeevan.Enums.OrderStage;
import com.ecommerce.HerbalJeevan.Enums.OrderStatus;

public class AdminOrders {
	
	private String orderId;
	private OrderStage stage;
	private OrderStatus status;
	private LocalDateTime orderDate;
	private String totalPrice;
	private List<AdminOrderResponse> orderItems;
	private Integer noOfItems;
	private Object buyer;
	private UserAddressResponse address;
	
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


		public Object getBuyer() {
			return buyer;
		}


		public void setBuyer(Object buyer) {
			this.buyer = buyer;
		}
	    
		public UserAddressResponse getAddress() {
			return address;
		}

		public void setAddress(UserAddressResponse userAddressResponse) {
			this.address = userAddressResponse;
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

		public List<AdminOrderResponse> getOrderItems() {
			return orderItems;
		}

		public void setOrderItems(List<AdminOrderResponse> orderItems) {
			this.orderItems = orderItems;
		}


		public Integer getNoOfItems() {
			return noOfItems;
		}


		public void setNoOfItems(Integer noOfItems) {
			this.noOfItems = noOfItems;
		}
		
		public void increaseQuantity(int count) {
			if(this.noOfItems==null) {
				this.noOfItems=count;
			}else {
				this.noOfItems+=count;

			}
		}
		
		

}
