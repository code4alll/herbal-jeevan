package com.ecommerce.HerbalJeevan.DTO;


public class CartItemsDto {
	private String itemName;
    private int quantity;
    private String gst;
    private String productId;
    private String currency;
    private String currencySymbol;
    private double sellPrice;
	private double unitPrice;
    private Integer minOrderQuant;
    private Integer unitsPerCarton;
    private Double totalAmount;
    private Double totalTax;;
    
    

	public Integer getMinOrderQuant() {
		return minOrderQuant;
	}

	public void setMinOrderQuant(Integer minOrderQuant) {
		this.minOrderQuant = minOrderQuant;
	}

	public Integer getUnitsPerCarton() {
		return unitsPerCarton;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setUnitsPerCarton(Integer unitsPerCarton) {
		this.unitsPerCarton = unitsPerCarton;
	}
	private SingleImageResponse image;


    
    public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

    
    
	public SingleImageResponse getImage() {
		return image;
	}

	public void setImage(SingleImageResponse image) {
		this.image = image;
	}

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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

	public Double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}
	
	
    
}
