package com.ecommerce.HerbalJeevan.DTO;


public class Invoice {
	
	private UserAddressResponse address;
	private OrderResponseDTO orderDetails;
	private String shippingService;
	private String buyer;
	private String transactionId;
	
	public OrderResponseDTO getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(OrderResponseDTO orderDetails) {
		this.orderDetails = orderDetails;
	}
	public String getShippingService() {
		return shippingService;
	}
	public void setShippingService(String shippingService) {
		this.shippingService = shippingService;
	}

	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public UserAddressResponse getAddress() {
		return address;
	}
	public void setAddress(UserAddressResponse address) {
		this.address = address;
	}
	
	
	

}
