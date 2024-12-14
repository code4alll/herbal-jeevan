package com.ecommerce.HerbalJeevan.DTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ProductFilterDTO {

    private List<String> category;
    private List<String> productType;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    
    private String productName;
    private String sellerId;
    
    private List<String> color;
    private List<String> brand;
    private List<String> stockLocation;
    private List<String> gender;
    private Set<String> searchItems;
    
    
    
	public Set<String> getSearchItems() {
		return searchItems;
	}
	public void setSearchItems(Set<String> searchItems) {
		this.searchItems = searchItems;
	}
	public List<String> getColor() {
		return color;
	}
	public void setColor(List<String> color) {
		this.color = color;
	}
	public List<String> getBrand() {
		return brand;
	}
	public void setBrand(List<String> brand) {
		this.brand = brand;
	}
	public List<String> getStockLocation() {
		return stockLocation;
	}
	public void setStockLocation(List<String> stockLocation) {
		this.stockLocation = stockLocation;
	}
	public List<String> getGender() {
		return gender;
	}
	public void setGender(List<String> gender) {
		this.gender = gender;
	}
	
	public List<String> getCategory() {
		return category;
	}
	public void setCategory(List<String> category) {
		this.category = category;
	}
	public List<String> getProductType() {
		return productType;
	}
	public void setProductType(List<String> productType) {
		this.productType = productType;
	}
	public BigDecimal getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
    


}
