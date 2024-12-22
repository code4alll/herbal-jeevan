package com.ecommerce.HerbalJeevan.DTO;


public class SingleImageResponse {
	private String imageUrl;
	private String imageSize;
	private String imageName;
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageSize() {
		return imageSize;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public SingleImageResponse(String imageUrl, Long imageSize, String imageName) {
		super();
		this.imageUrl = imageUrl;
//		this.imageSize = imageSize;
		this.imageName = imageName;
		double sizeInKB =  imageSize/ 1024.0;
        String formattedSize = String.format("%.1fKB", sizeInKB);
        this.imageSize=formattedSize;
	}
	public SingleImageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
