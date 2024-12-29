package com.ecommerce.HerbalJeevan.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
    	if(file==null||file.isEmpty()) {
    		return null;
    	}
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString(); // Return the URL of the uploaded image
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    public boolean deleteImage(String imageUrl) {
        try {
        	if(imageUrl==null) {
        		return false;
        	}
            // Extract the public ID from the URL
            String publicId = extractPublicIdFromUrl(imageUrl);

            // Call the Cloudinary API to delete the image
            Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            // Check if the deletion was successful
            return "ok".equals(deleteResult.get("result"));
        } catch (Exception e) {
        	return false;
        }
    }

    private String extractPublicIdFromUrl(String imageUrl) {
        // Assuming the public ID is the part of the URL after the folder and before the file extension
        // Example URL: https://res.cloudinary.com/demo/image/upload/v12345678/sample.jpg
        // Public ID: "sample"
        String[] urlParts = imageUrl.split("/");
        String fileNameWithExtension = urlParts[urlParts.length - 1];
        return fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
    }
}

