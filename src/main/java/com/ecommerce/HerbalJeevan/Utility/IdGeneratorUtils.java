package com.ecommerce.HerbalJeevan.Utility;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IdGeneratorUtils {
	
	public String generateOrderId() {
	    LocalDateTime now = LocalDateTime.now();
	    String uniqueId = UUID.randomUUID().toString().substring(0, 8); // Get first 8 characters of UUID
	    return String.format("ORD-%04d-%02d-%02d-%s", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), uniqueId);
	}
	
	public String generateCancelId() {
	    LocalDateTime now = LocalDateTime.now();
	    String uniqueId = UUID.randomUUID().toString().substring(0, 8); // Get first 8 characters of UUID
	    return String.format("CAN-%04d-%02d-%02d-%s", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), uniqueId);
	}
	
	public String generateProductId(String productName, String categoryName) {
        // Remove spaces and ensure productName and categoryName are not null and have at least 3 characters
        String productPrefix = (productName != null ? productName.replaceAll("\\s+", "") : "PRO");
        productPrefix = productPrefix.length() >= 3 ? productPrefix.substring(0, 3).toUpperCase() : productPrefix.toUpperCase();
        
        String categorySuffix = (categoryName != null ? categoryName.replaceAll("\\s+", "") : "CAT");
        categorySuffix = categorySuffix.length() >= 3 ? categorySuffix.substring(0, 3).toUpperCase() : categorySuffix.toUpperCase();
        
        // Generate a UUID and extract the first 8 characters
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Get the current timestamp formatted as yyyyMMddHHmmss
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        
        // Format the product ID
        return String.format("%s%s-%s-%s", productPrefix, categorySuffix, uuid, timestamp);
    }
	
	public String generateCategoryId(String categoryName) {
	    String categoryPrefix = categoryName.substring(0, Math.min(categoryName.length(), 3)).toUpperCase(); // Get first 3 characters of categoryName
	    String uuid = UUID.randomUUID().toString().substring(0, 6); // Get first 6 characters of UUID
	    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // Format current date as yyyyMMdd
	    
	    return String.format("%s-%s-%s", categoryPrefix, uuid, date);
	}
	
	public  String generateUrlSlug(String parentCategory, String categoryName) {
        // Combine parentCategory and categoryName
		 String combinedName=null;
		if(categoryName==null&&parentCategory!=null) {
			combinedName = parentCategory;
		}else if(categoryName!=null&&parentCategory==null) {
			combinedName = categoryName;

		}
		else {
			combinedName = parentCategory + "/" + categoryName;

		}
       

        // Convert to lowercase
        String slug = combinedName.toLowerCase();

        // Replace spaces with hyphens
        slug = slug.replaceAll("\\s+", "-");

        // Remove any non-alphanumeric characters except hyphens and slashes
        slug = slug.replaceAll("[^a-z0-9-\\/]", "");
        // Remove any consecutive hyphens
        slug = slug.replaceAll("-{2,}", "-");

        // Remove leading and trailing hyphens
        slug = slug.replaceAll("^-|-$", "");

        return slug;
    }
	
	 public static <E extends Enum<E>> E getEnumFromString(Class<E> enumClass, String value) {
	        if (enumClass == null || value == null) {
	            throw new IllegalArgumentException("Enum class and value must not be null");
	        }

	        try {
	            return Enum.valueOf(enumClass, value.toUpperCase());
	        } catch (IllegalArgumentException e) {
	            // Optionally, you can handle the exception or return a default value
	            throw new IllegalArgumentException("No enum constant " + enumClass.getCanonicalName() + "." + value, e);
	        }
	    }
	 
	 
	 private static final List<String> DATE_FORMATS = Arrays.asList(
		        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
		        "yyyy-MM-dd'T'HH:mm:ss.SSS",
		        "EEE, dd MMM yyyy HH:mm:ss zzz",
		        "yyyy-MM-dd",
		        "dd/MM/yyyy",
		        "MM/dd/yyyy",
		        "dd-MM-yyyy",
		        "MM-dd-yyyy"
		    );

		    public static Date convertStringToDate(String dateString) throws ParseException {
		    	if(dateString==null) {
		    		return null;
		    	}
		        for (String format : DATE_FORMATS) {
		            try {
		                return new SimpleDateFormat(format).parse(dateString);
		            } catch (ParseException e) {
		                // Continue to the next format
		            }
		        }
		        throw new ParseException("Unparseable date: " + dateString, -1);
		    }
	 
		    public static String cleanString(String input) {
		        if (input == null) {
		            return null;
		        }
		        return input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
		    }
	 
}
