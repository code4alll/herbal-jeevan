package com.ecommerce.HerbalJeevan.DTO;

import org.springframework.web.multipart.MultipartFile;

public class ProductImageDTO {
    private MultipartFile imageData;

	public MultipartFile getImageData() {
		return imageData;
	}

	public void setImageData(MultipartFile imageData) {
		this.imageData = imageData;
	}



}
