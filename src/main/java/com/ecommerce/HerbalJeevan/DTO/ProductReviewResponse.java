package com.ecommerce.HerbalJeevan.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ecommerce.HerbalJeevan.Enums.ReviewStatus;
import com.ecommerce.HerbalJeevan.Model.ProductReview;

public class ProductReviewResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7887065940142859640L;
	private Long id;
	private String productId;
    private int rating; // 1 to 5 or any custom range
    private String title;
    private String description;
    private LocalDateTime time;
    private ReviewStatus status;
    private String reviewerName;
    private String image1;
    private String image2;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public ReviewStatus getStatus() {
		return status;
	}
	public void setStatus(ReviewStatus status) {
		this.status = status;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public ProductReviewResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductReviewResponse(Long id, String productId, int rating, String title, String description,
			LocalDateTime time, ReviewStatus status, String reviewerName, String image1, String image2) {
		super();
		this.id = id;
		this.productId = productId;
		this.rating = rating;
		this.title = title;
		this.description = description;
		this.time = time;
		this.status = status;
		this.reviewerName = reviewerName;
		this.image1 = image1;
		this.image2 = image2;
	}
	
	public ProductReviewResponse(ProductReview r) {
		super();
		this.id = r.getId();
		this.productId = r.getProduct().getProductId();
		this.rating = r.getRating();
		this.title = r.getTitle();
		this.description = r.getDescription();
		this.time = r.getUpdatedAt();
		this.status = r.getStatus();
		this.reviewerName = r.getReviewerName();
		this.image1 = r.getImage1();
		this.image2 = r.getImage2();
	}
    
    

}
