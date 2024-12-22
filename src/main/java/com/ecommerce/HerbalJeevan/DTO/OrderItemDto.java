package com.ecommerce.HerbalJeevan.DTO;


public class OrderItemDto {
	
	private String itemName;
    private double itemPrice;
    private int quantity;
    private String gst;
    private Long itemId;
    private String productId;
    
    
    private double sellPrice;
    
    private Double totalAmount;
    
    private Double totalTax;
    
    
    private String imageUrl;  
    private String size;
    private String name;  
    
    
    private String currency;
    private String currencySymbol;
    
    private SellerDetailsResponse seller;
    
    
    
    
    
	public SellerDetailsResponse getSeller() {
		return seller;
	}


	public void setSeller(SellerDetailsResponse seller) {
		this.seller = seller;
	}


	public double getSellPrice() {
		return sellPrice;
	}


	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}


	public Double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public Double getTotalTax() {
		return totalTax;
	}


	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public String getItemName() {
		return itemName;
	}
	
	
	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public OrderItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderItemDto(String itemName, double itemPrice, int quantity, String gst, Long itemId) {
		super();
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.quantity = quantity;
		this.gst = gst;
		this.itemId = itemId;
	}
	
	
    
    

}
