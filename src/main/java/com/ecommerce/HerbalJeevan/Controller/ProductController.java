package com.ecommerce.HerbalJeevan.Controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.HerbalJeevan.DTO.ImageResource;
import com.ecommerce.HerbalJeevan.DTO.ProductFilterDTO;
import com.ecommerce.HerbalJeevan.DTO.ProductImageDTO;
import com.ecommerce.HerbalJeevan.DTO.ProductResponse;
import com.ecommerce.HerbalJeevan.DTO.productdto;
import com.ecommerce.HerbalJeevan.Enums.SortOption;
import com.ecommerce.HerbalJeevan.Model.Admin;
import com.ecommerce.HerbalJeevan.Service.ProductServiceImp;
import com.ecommerce.HerbalJeevan.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductServiceImp productService;
	@Autowired
	private UserService userService;
	
	


	@PostMapping("/AddProduct")
	private ResponseEntity<?> AddProduct(
	        @RequestParam("productData") String productData,
	        @RequestParam("productImage") MultipartFile[] file
	) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Logger logger = LoggerFactory.getLogger(getClass()); // Use a logger for better logging

	    try {
	        logger.info("Received product data: {}", productData);

	        // Deserialize product data
	        ObjectMapper objectMapper = new ObjectMapper();
	        productdto res = objectMapper.readValue(productData, productdto.class);
	        
	        // Handle invalid product data or unauthorized user
	        if (res == null || !userService.hasSellerAuthority(authentication)) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized");
	        }

	        // Process product image
	        List<ProductImageDTO> productImage = productService.getImageDto(file);
	        if (productImage == null || StringUtils.isBlank(res.getCategoryPath())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Please add category path");
	        }

	        // Add product
	        Admin user = userService.getAdminDetails(userService.extractUsernameFromPrincipal(authentication));
	        boolean status = productService.addProduct(res, productImage, user);

	        if (status) {
	            return ResponseEntity.status(HttpStatus.OK).body("Product added successfully");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Something went wrong while adding the product");
	        }
	    } catch (Exception e) {
	        logger.error("Error while adding product", e); // Log the full error
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Something went wrong while adding the product: " + e.getMessage());
	    }
	}

	@GetMapping("/getproducts")
    public ResponseEntity<?> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,           
            @RequestParam(required=false) String category,
            @RequestParam(defaultValue = "NEWEST") SortOption sort,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> brand,
            @RequestParam(required = false) List<String> stockLocation,
            @RequestParam(required = false) List<String> gender,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String seller,
            @RequestParam(required=false) List<String> productType
    		) {
		 // Parse sorting parameter
       

        Pageable pageable = PageRequest.of(page, size);
        

        ProductFilterDTO filter = new ProductFilterDTO();
        filter.setCategory(productType);
        filter.setMinPrice(minPrice);
        filter.setMaxPrice(maxPrice);
        filter.setProductName(productName);
        filter.setSellerId(seller);
        filter.setColor(color);
        filter.setBrand(brand);
        filter.setStockLocation(stockLocation);
        filter.setGender(gender);
        filter.setProductType(productType);
        Page<ProductResponse> productPage = productService.getAllProductsWithImages(pageable, search,category,filter,sort);
//        Page<ProductResponse> productPage = productService.getAllProductsWithImages(pageable, search,sort);
        Map<String, Object> response = new HashMap<>();
        response.put("data", productPage.getContent());
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());
        response.put("hasNext", productPage.hasNext());
        response.put("hasPrevious", productPage.hasPrevious());
        response.put("isFirst", productPage.isFirst());
        response.put("isLast", productPage.isLast());
        response.put("pageSize", productPage.getSize());
        response.put("sort", productPage.getSort());
        response.put("numberOfElements", productPage.getNumberOfElements());
        response.put("firstPage", productPage.isFirst());
        response.put("lastPage", productPage.isLast());

        return ResponseEntity.ok().body(response);
        
        
		
		
		
		
		
	}
	
	@ApiIgnore
	@GetMapping("/image/{filename}")
	public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
//		try {
//			// Load the image as a Resource
//			Path imagePath = Paths.get(imageUploadDir).resolve(filename);
//			Resource resource = new UrlResource(imagePath.toUri());
//
//			if (resource.exists() && resource.isReadable()) {
//				return ResponseEntity.ok()
//						.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(imagePath))
//						.body(resource);
//			} else {
//				return ResponseEntity.notFound().build();
//			}
//		} catch (IOException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
		
		
		
		 try {
	            // Fetch the image using the ImageService
	            ImageResource resource = productService.fetchImage(filename);
//				Resource resource = new UrlResource(imagePath.toUri());

	            // Extract content type
	            

	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_TYPE, resource.getContentType())
	                    .body(resource.getResource());
	        } catch (Exception e) {
	        	e.printStackTrace();
	            // Handle exceptions (e.g., image not found in S3)
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	}

}
