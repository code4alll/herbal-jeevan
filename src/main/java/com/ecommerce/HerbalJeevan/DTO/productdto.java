package com.ecommerce.HerbalJeevan.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class productdto {
	private String categoryPath;
	private String stock;
	private String originalPrice;
	private String salePrice;
	private String oneStar;
	private String twoStar;
	private String threeStar;
	private String fourStar;
	private String fiveStar;
	private String name;
	private String info;
	
	
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getOneStar() {
		return oneStar;
	}
	public void setOneStar(String oneStar) {
		this.oneStar = oneStar;
	}
	public String getTwoStar() {
		return twoStar;
	}
	public void setTwoStar(String twoStar) {
		this.twoStar = twoStar;
	}
	public String getThreeStar() {
		return threeStar;
	}
	public void setThreeStar(String threeStar) {
		this.threeStar = threeStar;
	}
	public String getFourStar() {
		return fourStar;
	}
	public void setFourStar(String fourStar) {
		this.fourStar = fourStar;
	}
	public String getFiveStar() {
		return fiveStar;
	}
	public void setFiveStar(String fiveStar) {
		this.fiveStar = fiveStar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	

}
