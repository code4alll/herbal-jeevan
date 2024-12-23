package com.ecommerce.HerbalJeevan.DTO;

public class ReviewDto {
	private String productId;
	private String comment;
	public ReviewDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Integer rating;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	
	

}
