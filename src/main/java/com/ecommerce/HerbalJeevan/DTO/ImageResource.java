package com.ecommerce.HerbalJeevan.DTO;

import org.springframework.core.io.Resource;

public class ImageResource {
	
	private final Resource resource;
    private final String contentType;

    public ImageResource(Resource resource, String contentType) {
        this.resource = resource;
        this.contentType = contentType;
    }

	public Resource getResource() {
		return resource;
	}

	public String getContentType() {
		return contentType;
	}
    
    

}
