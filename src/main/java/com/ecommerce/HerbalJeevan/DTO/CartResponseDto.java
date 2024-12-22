package com.ecommerce.HerbalJeevan.DTO;


import java.util.List;

public class CartResponseDto {
	
	    public double getTotalUnitGstPrice() {
		return totalUnitGstPrice;
	}

	public void setTotalUnitGstPrice(double totalUnitGstPrice) {
		this.totalUnitGstPrice = totalUnitGstPrice;
	}

	public double getTotalSellGstPrice() {
		return totalSellGstPrice;
	}

	public void setTotalSellGstPrice(double totalSellGstPrice) {
		this.totalSellGstPrice = totalSellGstPrice;
	}

		public CartResponseDto(String currency, String currencySymbol, double totalSellPrice) {
		super();
		this.currency = currency;
		this.currencySymbol = currencySymbol;
		this.totalSellPrice = totalSellPrice;
	}

		private List<CartItemsDto> cartItems;
	    private String currency;
	    private String currencySymbol;
	    private double totalUnitPrice;
	    private double totalSellPrice;
	    private double totalUnitGstPrice;
	    private double totalSellGstPrice;
	    
	    
	   

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

		


		public CartResponseDto(List<CartItemsDto> cartItems, String currency, String currencySymbol,
				double totalUnitPrice, double totalSellPrice) {
			super();
			this.cartItems = cartItems;
			this.currency = currency;
			this.currencySymbol = currencySymbol;
			this.totalUnitPrice = totalUnitPrice;
			this.totalSellPrice = totalSellPrice;
		}

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

		public CartResponseDto() {
			// TODO Auto-generated constructor stub
		}

		public List<CartItemsDto> getCartItems() {
			return cartItems;
		}

		public void setCartItems(List<CartItemsDto> cartItems) {
			this.cartItems = cartItems;
		}

	
	    
	    
	    
	    

}
