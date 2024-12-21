package com.ecommerce.HerbalJeevan.DTO;

import java.io.Serializable;
import java.util.List;

import com.ecommerce.HerbalJeevan.Model.ProductReview;
import com.ecommerce.HerbalJeevan.Service.Categorizable;

public class SingleProductDTO implements Serializable,Categorizable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3944260352206480875L;
	private String productId;
    private List<ImageDto> images;
	private List<ProductReview> reviews;  
	private String categoryPath; 
    private String stock;
	private String originalPrice;
	private String salePrice;
	private String oneStar;
	private String twoStar;
	private String threeStar;
	private String fourStar;
	private String fiveStar;
	private String info;
	private String name;
    private String sku;
    private String currencyName;
    private String selectedSupOption;
    private String selectedSubOption;
    private String selectedMiniSubOption;
    private String selectedMicroSubOption;
    
    private SellerDetailsResponse seller;
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public SellerDetailsResponse getSeller() {
		return seller;
	}
	public void setSeller(SellerDetailsResponse seller) {
		this.seller = seller;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<ImageDto> getImages() {
		return images;
	}
	public void setImages(List<ImageDto> images) {
		this.images = images;
	}
	public List<ProductReview> getReviews() {
		return reviews;
	}
	public void setReviews(List<ProductReview> reviews) {
		this.reviews = reviews;
	}
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSelectedSupOption() {
		return selectedSupOption;
	}
	public void setSelectedSupOption(String selectedSupOption) {
		this.selectedSupOption = selectedSupOption;
	}
	public String getSelectedSubOption() {
		return selectedSubOption;
	}
	public void setSelectedSubOption(String selectedSubOption) {
		this.selectedSubOption = selectedSubOption;
	}
	public String getSelectedMiniSubOption() {
		return selectedMiniSubOption;
	}
	public void setSelectedMiniSubOption(String selectedMiniSubOption) {
		this.selectedMiniSubOption = selectedMiniSubOption;
	}
	public String getSelectedMicroSubOption() {
		return selectedMicroSubOption;
	}
	public void setSelectedMicroSubOption(String selectedMicroSubOption) {
		this.selectedMicroSubOption = selectedMicroSubOption;
	}
	public void setCurrencyname(String string) {
		this.currencyName=string;
		
	}
    
    
}
