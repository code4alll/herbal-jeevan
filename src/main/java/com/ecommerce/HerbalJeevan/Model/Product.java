package com.ecommerce.HerbalJeevan.Model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.ecommerce.HerbalJeevan.Validation.ProductValidValues;
import com.nimbusds.oauth2.sdk.util.StringUtils;


@Entity
@Table(name="product")
@ProductValidValues
public class Product implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
	private String Id;
	
    @Column(name = "product_id")
    private String productId;
    
    
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.LAZY)
	private List<ProductReview> reviews;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.LAZY)
	private List<ProductQuestion> questions;

	
	
//	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PriceList> priceList;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    
    @Column(name="category_path")
	private String categoryPath;
	
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Admin seller;
    
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
    private String availability;
   @Column(name="quantity",columnDefinition = "100")
    private String quantity;

	
	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	
	
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<ProductImage> getImages() {
		return images;
	}
	public void setImages(List<ProductImage> images) {
		this.images = images;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public Admin getSeller() {
		return seller;
	}
	public void setSeller(Admin seller) {
		this.seller = seller;
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
	public List<ProductReview> getReviews() {
		return reviews;
	}
	public void setReviews(List<ProductReview> reviews) {
		this.reviews = reviews;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDate getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Boolean isAvailable() {
		return StringUtils.isNotBlank(this.quantity)&&Integer.valueOf(this.quantity)>0;
	}
	
    
        
    
}
