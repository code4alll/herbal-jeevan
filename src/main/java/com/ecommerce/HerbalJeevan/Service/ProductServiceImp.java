package com.ecommerce.HerbalJeevan.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.HerbalJeevan.DTO.FileDetailsVo;
import com.ecommerce.HerbalJeevan.DTO.ImageDto;
import com.ecommerce.HerbalJeevan.DTO.ImageResource;
import com.ecommerce.HerbalJeevan.DTO.PageResponse;
import com.ecommerce.HerbalJeevan.DTO.ProductFilterDTO;
import com.ecommerce.HerbalJeevan.DTO.ProductImageDTO;
import com.ecommerce.HerbalJeevan.DTO.ProductQuestionsResponse;
import com.ecommerce.HerbalJeevan.DTO.ProductResponse;
import com.ecommerce.HerbalJeevan.DTO.ProductReviewResponse;
import com.ecommerce.HerbalJeevan.DTO.ReviewDto;
import com.ecommerce.HerbalJeevan.DTO.SellerDetailsResponse;
import com.ecommerce.HerbalJeevan.DTO.SingleImageResponse;
import com.ecommerce.HerbalJeevan.DTO.SingleProductDTO;
import com.ecommerce.HerbalJeevan.DTO.imageUploadDTO;
import com.ecommerce.HerbalJeevan.DTO.productdto;
import com.ecommerce.HerbalJeevan.Enums.QuestionStatus;
import com.ecommerce.HerbalJeevan.Enums.ReviewStatus;
import com.ecommerce.HerbalJeevan.Enums.SortOption;
import com.ecommerce.HerbalJeevan.Model.Admin;
import com.ecommerce.HerbalJeevan.Model.Category;
import com.ecommerce.HerbalJeevan.Model.Product;
import com.ecommerce.HerbalJeevan.Model.ProductImage;
import com.ecommerce.HerbalJeevan.Model.ProductQuestion;
import com.ecommerce.HerbalJeevan.Model.ProductReview;
import com.ecommerce.HerbalJeevan.Model.User;
import com.ecommerce.HerbalJeevan.Repository.CategoryRepository;
import com.ecommerce.HerbalJeevan.Repository.ImageRepository;
import com.ecommerce.HerbalJeevan.Repository.ProductQuestionRepository;
import com.ecommerce.HerbalJeevan.Repository.ProductRepository;
import com.ecommerce.HerbalJeevan.Repository.ProductReviewRepository;
import com.ecommerce.HerbalJeevan.Utility.IdGeneratorUtils;
import com.ecommerce.HerbalJeevan.Utility.NullAwareBeanUtilsBean;
import com.ecommerce.HerbalJeevan.Utility.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ProductServiceImp implements ProductService {
	
	
	@Value("${image.upload.dir}")
    private String imageUploadDir;
	@Value("${backend.url}")
	private String imagePath;
	@Value("${image.path}")
	private String backendImage;
	
//    @Value("${application.bucket.name}")
//    private String bucketName;
	

//    @Autowired
//    private AmazonS3 s3Client;
	

	
	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	ImageRepository imgRepo;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	IdGeneratorUtils IdGenerator;
	@Autowired
	UserService userService;
	
//	@Autowired
//	AmazonServices amazonService;
	
	@Autowired
	ImageRepository imageRepo;
	@Autowired
	private ProductRepository ProductRepo;
	
	@Autowired
	private ImageUploadService imageUploadService;
	
	@Autowired
	private ProductReviewRepository reviewRepo;
	
	@Autowired
	private ProductQuestionRepository questionRepo;

	@Override
	public boolean addProduct(productdto productDTO, List<ProductImageDTO> imageDTO, Admin user) throws IOException {
    	Boolean status=false;
    	try {
        	Product product = convertToProductEntity(productDTO);
            Category category=categoryService.resolveCategory(productDTO.getCategoryPath());
            product.setCategory(category);
//            product.setAddedBy(user.getUsername());
            status=saveProductWithImages(product,imageDTO,user);
            if(product!=null&&status) {
                return true;

            }
            else {
            	return false;
            }
        }catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
    	

    }
	
	
	@SuppressWarnings("unused")
	private boolean saveProductWithImages(Product product, List<ProductImageDTO> imageDTOs, Admin user) throws IOException {
	        List<String> savedImagePaths = new ArrayList<>();
	    	List<ProductImage> productImages = new ArrayList<>();
	    	
	    	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Map<Integer, Set<ConstraintViolation<Product>>> violationMap = new HashMap<>();
			Map<String, List<String>> mapObj = new HashMap<String, List<String>>();
			Set<ConstraintViolation<Product>> violations = new HashSet<>();



	  try {
		getImageList(imageDTOs,product,savedImagePaths,productImages);
	    product.setImages(productImages);
	    product.setProductId(IdGenerator.generateProductId(product.getName(), product.getCategory().getName()));
	    if(product.getQuantity()==null) {
	    	product.setQuantity("100");
	    }
	    if(product.getStock()==null) {
	    	product.setStock("100");
	    }
	    
		violations = validator.validate(product);
		if(violations.isEmpty()) {
			product.setSeller(user);
		    ProductRepo.save(product);
		    return true;

		}else {
			System.out.println(violations);
		    return false;

		}

	    
	    }
	  catch(Exception e) {
		  for (String imagePath : savedImagePaths) {
	          deleteImage(imagePath);
	      }
	     e.printStackTrace(); 
	     return false;
	        
	        
	    }
	    }

	  
	  


	@Override
	public Response<?> deleteProductWithImagesAndPriceList(String id) {
		try {
			 Product product = ProductRepo.findByProductId(id).orElse(null);
		        if (product != null) {
		            // Delete associated images
		            List<ProductImage> productImages = product.getImages();
		            for (ProductImage image : productImages) {
		            	imageUploadService.deleteImage(image.getImageUrl());
		            }            
		            ProductRepo.deleteById(product.getId());
		            return new Response<>(true,"Product deleted sucessfully","Product deleted sucessfully");
		        } else {
					return new Response<>(false,"Product not found with ID: " + id);
		        }
		}catch(Exception e) {
			return new Response<>(false,"error while deleting product ","error while deleting product "+e.getCause()+" "+e.getMessage());
		}
       
    }

	@Override
	public void deleteProductWithImages(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String ImageEncodeDecode(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteProduct(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileDetailsVo saveProductBulkUploadData(Map<Integer, Product> dataMap, String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Categorizable> void setCategories(T productResponse, String categoryPath) {

        String[] categories = categoryPath.split("/");

        // Set categories in productResponse
        if (categories.length > 0) {
            productResponse.setSelectedSupOption(categories[0]);
        } else {
            productResponse.setSelectedSupOption("");
        }

        if (categories.length > 1) {
            productResponse.setSelectedSubOption(categories[1]);
        } else {
            productResponse.setSelectedSubOption("");
        }

        if (categories.length > 2) {
            productResponse.setSelectedMiniSubOption(categories[2]);
        } else {
            productResponse.setSelectedMiniSubOption("");
        }

        if (categories.length > 3) {
            productResponse.setSelectedMicroSubOption(categories[3]);
        } else {
            productResponse.setSelectedMicroSubOption("");
        }
    		
	}

	@Override
	public Optional<SingleProductDTO> getProductById(String productId) {
    	SingleProductDTO response=new SingleProductDTO();

    	try {
    		
    		Optional<Product> repoProduct=ProductRepo.findProductByProductId(productId);
    		
    		if(repoProduct.isPresent()) {

    			Product product=repoProduct.get();
	            BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
        		Admin seller=product.getSeller();

        		product.setSeller(null);
	            notNull.copyProperties(response, product);
        		List<ImageDto> img=getImages(product.getImages());
        		response.setImage(img);
        		response.setCategoryPath(product.getCategory().getUrlSlug());
        		response.setCurrencyname("");
        		response.setSalePrice(product.getSalePrice());
        		response.setCategoryPath(product.getCategory().getUrlSlug());
        		response.setOriginalPrice(product.getOriginalPrice());
        		setCategories(response,product.getCategoryPath());
        		if(seller!=null) {
        			SellerDetailsResponse s=new SellerDetailsResponse();
        			s.setCompanyName("");
        			s.setCountryOfoperation("India");
        			s.setIsVerified(seller.getIsVerified());
        			s.setName(seller.getFirstname()+" "+seller.getLastname());
        			
        			response.setSeller(s);
        		}
        		response.calculateOverallRating(product);
        		response.setReview(getProductReviewResponse(product.getReviews()));
        		response.setQuestion(getProductQuestionResponse(product.getQuestions()));
    			
    			
    		}
    		return Optional.of(response);
    		
    		
    		
    	}catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("Error copying properties from Product to ProductDTO: " + e.getMessage());
            Throwable targetException = ((InvocationTargetException) e).getTargetException();
            if (targetException != null) {
                targetException.printStackTrace();
            } else {
                e.printStackTrace();
            }
    	}
    	return null;
    }
	
	public List<ProductReviewResponse> getProductReviewResponse(List<ProductReview> review) {
	    if (review == null || review.isEmpty()) {
	        return null;
	    }
	    return review.stream()
	                 .map(ProductReviewResponse::new) // Calls the constructor with ProductReview as an argument
	                 .collect(Collectors.toList());
	}
	public List<ProductQuestionsResponse> getProductQuestionResponse(List<ProductQuestion> question){
		 if (question == null || question.isEmpty()) {
		        return null;
		    }
		    return question.stream()
		                 .map(ProductQuestionsResponse::new) // Calls the constructor with ProductReview as an argument
		                 .collect(Collectors.toList());
	}

	@Override
	public List<ProductImageDTO> getImageDto(MultipartFile[] file) {
	     List<ProductImageDTO> productImage=new ArrayList<>();

		if(file!=null) {
			for(int i=0;i<file.length;i++) {
				ProductImageDTO image=new ProductImageDTO();
				image.setImageData(file[i]);
				productImage.add(image);
		
			}
		}
		return productImage;
		
	}

	@Override
	public void getImageList(List<ProductImageDTO> imageDTOs,Product product,List<String> savedImagePaths,List<ProductImage> productImages) throws IOException{
	    int nextPriority = 1;

		if(imageDTOs!=null) {
			
		    for (ProductImageDTO imageDTO : imageDTOs) {
		        // Save the image file to the server
		        MultipartFile imageFile = imageDTO.getImageData();
	            imageUploadDTO fileName = getImageUrl(imageFile);
		        savedImagePaths.add(fileName.getUrl());
		        
		        int priority = extractPriority(imageDTO.getImageData().getOriginalFilename());

		        

		        ProductImage productImage = new ProductImage();
		        productImage.setImageUrl(fileName.getUrl()); 
		        productImage.setProduct(product); 
		        productImage.setSku(product.getSku());
		        productImage.setName(imageFile.getOriginalFilename());
		        productImage.setSize(imageFile.getSize());
		        productImage.setDimension(fileName.getHeight()+" x "+fileName.getWidth());
		        productImage.setPriority( priority == 0 ? nextPriority++ : priority);

		        productImages.add(productImage);
		    }
		}
	}

	@Override
	public String encodeImage(String imagePath) {
		// TODO Auto-generated method stub
		return null;
	}
	
	   
    private int extractPriority(String fileName) {
    	
        int lastDotIndex = fileName.lastIndexOf('.');
        String parts = fileName.substring(0, lastDotIndex);

    	if (parts != null) {
            if (Character.isDigit(parts.charAt(parts.length() - 1))) {
                return Character.getNumericValue(parts.charAt(parts.length() - 1));
            }
           
        }
    	
        
        return 0; // No priority found
    }

	@Override
	public String decodeBase64ToImage(String base64Image, String outputImagePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double CalculateUnitPrice(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double CalculateSellPrice(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double CalculateUnitPriceCart(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double CalculateSellPriceCart(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProductResponse> getAllProductsWithImages(Pageable pageable, String search, SortOption sort) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	 public Page<ProductResponse> getAllProductsWithImages(Pageable pageable, String search, String category, ProductFilterDTO filter, SortOption sort) {
	     Page<Product> products;
	     Long total=0l;
	     if (search == null) {
	         products = getProductsByCategory(category, pageable);
	         
	     } else {
	         products = getProductsBySearchAndCategory(search, category, pageable);
	     }   
	     total=products.getTotalElements();   
	     List<ProductResponse> productResponses = filterAndSortProducts(products, filter, "india", sort);

	    
//	     int start = Math.min((int) pageable.getOffset(), productResponses.size());
//	     int end = Math.min(start + pageable.getPageSize(), productResponses.size());

	     return new PageImpl<>(productResponses, pageable, total);
	 }
	
	
	 private Page<Product> getProductsByCategory(String category, Pageable pageable) {
	     if (category != null) {
	         Category categoryEntity = categoryService.findByName(category);
	         if (categoryEntity != null) {
	             List<Category> categories = categoryService.getAllSubCategories(categoryEntity);
	             return ProductRepo.findByCategories(categories, pageable);
	         }
	     }
	        return ProductRepo.findAll(pageable);
	 }
	 
	 private Page<Product> getProductsBySearchAndCategory(String search, String category, Pageable pageable) {
	     if (category != null) {
	         Category categoryEntity = categoryService.findByName(category);
	         if (categoryEntity != null) {
	             List<Category> categories = categoryService.getAllSubCategories(categoryEntity);
	             
	                return ProductRepo.findByCategoriesAndSearchterm(categories, search, pageable);
	              
	         }
	     }
	    return ProductRepo.findSimilarProducts(search.toLowerCase(), pageable);
	 }
	 
	 private List<ProductResponse> filterAndSortProducts(Page<Product> products, ProductFilterDTO filter, String country, SortOption sort) {
	     return products.stream()
	         .map(product -> mapToProductResponse(product, country))
	         .filter(product -> filter.getCategory() == null || filter.getCategory().isEmpty()
	                 || filter.getCategory().stream().anyMatch(cat ->
	                 cat.equalsIgnoreCase(product.getSelectedSupOption())
	                         || cat.equalsIgnoreCase(product.getSelectedSubOption())
	                         || cat.equalsIgnoreCase(product.getSelectedMiniSubOption())
	                         || cat.equalsIgnoreCase(product.getSelectedMicroSubOption())))
	         .filter(product -> filter.getMinPrice() == null || convertToBigDecimal(product.getSalePrice()).compareTo(filter.getMinPrice()) >= 0)
	         .filter(product -> filter.getMaxPrice() == null || convertToBigDecimal(product.getOriginalPrice()).compareTo(filter.getMaxPrice()) <= 0)
	         .filter(product -> filter.getProductName() == null || product.getName().toLowerCase().contains(filter.getProductName().toLowerCase()))
//	         .filter(product -> filter.getSellerId() == null || product.getAddedBy().equals(filter.getSellerId()))
//	         .filter(product -> filter.getColor() == null || filter.getColor().isEmpty() || filter.getColor().contains(product.getColors()))
//	         .filter(product -> filter.getBrand() == null || filter.getBrand().isEmpty() || filter.getBrand().contains(product.getBrandName()))
//	         .filter(product -> filter.getStockLocation() == null || filter.getStockLocation().isEmpty() || filter.getStockLocation().contains(product.getStockLocation()))
//	         .filter(product -> filter.getGender() == null || filter.getGender().isEmpty() || filter.getGender().contains(product.getGender()))
	         .sorted(getComparator(sort))
	         .collect(Collectors.toList());
	 }
	 
	 private BigDecimal convertToBigDecimal(String price) {
	        try {
	            return new BigDecimal(price);
	        } catch (NumberFormatException e) {
	            return BigDecimal.ZERO;
	        }
	    } 

//	@Override
//	 public ImageResource fetchImage(String filename) throws Exception {
//        // Construct the full path to the image in S3
//        String s3Key = imageUploadDir + "" + filename;
////        String key="ProductImages/de7ec5f7-1369-45c2-8e3e-c10dc6a8f0e1_table.png";
//
//        // Fetch the image from S3
//        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, s3Key));
//
//        // Get the content type from the S3 object metadata
//        String contentType = s3Object.getObjectMetadata().getContentType();
//
//        // Create a Resource from the S3 object input stream
//        Resource resource = new InputStreamResource(s3Object.getObjectContent());
//
//        // Return both the resource and the content type
//        return new ImageResource(resource, contentType);
//    }
	
	private Comparator<? super ProductResponse> getComparator(SortOption sort) {

        switch (sort) {
            case PRICE_LOW_TO_HIGH:
                return Comparator.comparing(product -> toDouble(product.getSalePrice()));
            case PRICE_HIGH_TO_LOW:
                return Comparator.comparing((ProductResponse product) -> toDouble(product.getSalePrice())).reversed();
            case RATING:
                return Comparator.comparing(ProductResponse::getFiveStar).reversed();
            case SALES:
                return Comparator.comparing(ProductResponse::getStock).reversed();
            case NEWEST:
                return Comparator.comparing(ProductResponse::getTime).reversed();
            case RELEVANCE:
            default:
                return Comparator.comparing(ProductResponse::getTime).reversed();
        }
    }

	private double toDouble(String value) {
        try {
        	if(value==null) {
        		return 0;
        	}
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
	@Override
	public ImageResource fetchImage(String filename) throws Exception {
	    // Construct the full path to the image in the local directory
	    String localImagePath = imageUploadDir+""+ filename;

	    // Create a File object to access the image
	    File imageFile = new File(localImagePath);
	    if (!imageFile.exists()) {
	        throw new Exception("Image not found at the specified path: " + localImagePath);
	    }

	    // Get the content type (you could also use a library to determine the MIME type, e.g., Apache Tika)
	    String contentType = Files.probeContentType(imageFile.toPath());

	    // Create an InputStream from the file
	    InputStream inputStream = new FileInputStream(imageFile);

	    // Wrap the InputStream in a Spring Resource (e.g., InputStreamResource)
	    Resource resource = new InputStreamResource(inputStream);

	    // Return the resource along with the content type
	    return new ImageResource(resource, contentType);
	}


	@Override
	public boolean updateProduct(productdto res, List<ProductImageDTO> productImage, Product oldProduct) {
		List<String> savedImagesPath=new ArrayList<>();
    	List<ProductImage> productImages = new ArrayList<>();
    	Product product=convertToProductEntity(res);


		
		try {
			getImageList(productImage,oldProduct,savedImagesPath,productImages);
			product.setId(oldProduct.getId());
			product.setProductId(oldProduct.getProductId());
			product.setImages(oldProduct.getImages());
			oldProduct.getImages().addAll(productImages);
            BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
            notNull.copyProperties(oldProduct,product);
            imgRepo.saveAll(productImages);
            ProductRepo.save(oldProduct);
            
            

			return true;
			
		}catch(Exception  e) {

			  for (String imagePath : savedImagesPath) {
		          deleteImage(imagePath);
		      }
		     e.printStackTrace();
		     return false;
		   	
		}
		
	}

	@Override
	public void deleteImage(String fileName) {
	      String filePath = imageUploadDir + fileName;
	      File imageFile = new File(filePath);
	      if (imageFile.exists()) {
	          imageFile.delete();
	      }
	  }

//	@Override
//	 public imageUploadDTO getImageUrl(MultipartFile imageFile) throws IOException {
//	      String originalFilename = imageFile.getOriginalFilename();
//	      if (originalFilename == null) {
//	          throw new IOException("Original filename is null");
//	      }
//
//	      BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
//	      if (bufferedImage == null) {
//	          throw new IOException("Failed to read image file");
//	      }
//	      int width = bufferedImage.getWidth();
//	      int height = bufferedImage.getHeight();
//
//	      // Generate a unique filename
//	      String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
//
//	      // Convert BufferedImage to InputStream
//	      ByteArrayOutputStream os = new ByteArrayOutputStream();
//	      String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
//	      ImageIO.write(bufferedImage, fileExtension, os);
//	      byte[] byteArray = os.toByteArray();
//	      ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
//
//	      // Upload to S3
////	      ObjectMetadata metadata = new ObjectMetadata();
////	      metadata.setContentType(imageFile.getContentType());
////	      metadata.setContentLength(byteArray.length);
////	      s3Client.putObject(new PutObjectRequest(bucketName, imageUploadDir + "" + fileName, inputStream, metadata));
//
//	      // Return the image metadata and S3 URL
//	      imageUploadDTO imgdto = new imageUploadDTO();
//	      imgdto.setHeight(height);
//	      imgdto.setWidth(width);
//	      imgdto.setUrl(imagePath + "/" + fileName);
//
//	      return imgdto;
//	  }
	
	@Override
	public imageUploadDTO getImageUrl(MultipartFile imageFile) throws IOException {
	    String originalFilename = imageFile.getOriginalFilename();
	    if (originalFilename == null) {
	        throw new IOException("Original filename is null");
	    }

	    // Read the image from the MultipartFile
	    BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
	    if (bufferedImage == null) {
	        throw new IOException("Failed to read image file");
	    }
	    int width = bufferedImage.getWidth();
	    int height = bufferedImage.getHeight();

	    // Generate a unique filename
//	    String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

	    // Define the local directory to save the image (c/images)

	    // Ensure the directory exists
//	    File directory = new File(imageUploadDir);
//	    if (!directory.exists()) {
//	        directory.mkdirs();  // Create the directory if it doesn't exist
//	    }
//
//	    // Create the local file path (path to save the image)
//	    File localFile = new File(imageUploadDir + fileName);
//
//	    // Write the image to the local filesystem
//	    ImageIO.write(bufferedImage, originalFilename.substring(originalFilename.lastIndexOf('.') + 1), localFile);

	    // Create and return the image metadata (local URL or file path)
	    String imageUrl=imageUploadService.uploadImage(imageFile);
	    imageUploadDTO imgdto = new imageUploadDTO();
	    imgdto.setHeight(height);
	    imgdto.setWidth(width);
	    imgdto.setUrl(imageUrl); // This will return the local file path (e.g., file:///c/images/filename)

	    return imgdto;
	}


	@Override
	public Product convertToProductEntity(productdto productDTO) { Product product = new Product();
    try {
		BeanUtils.copyProperties(product, productDTO);
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    return product;}

	@Override
	public List<ImageDto> getImages(List<ProductImage> image) {
    	List<ImageDto> list=new ArrayList<>();
        image.sort(Comparator.comparingInt(ProductImage::getPriority));

    	image.forEach(f->{
    		ImageDto images=new ImageDto();
    		images.setImageUrl(f.getImageUrl());
    		images.setName(f.getName());
    		double sizeInKB = f.getSize() / 1024.0;
            String formattedSize = String.format("%.1fKB", sizeInKB);
    		images.setSize(formattedSize);
    		images.setDimension(f.getDimension());
    		images.setImageId(f.getId());
    		list.add(images);
    	});
    	list.sort(Comparator.comparingInt(ImageDto::getPriority));
    	
    	return list;
    }

	@Override
	public ProductResponse mapToProductResponse(Product product, String country) {
        ProductResponse productResponse = new ProductResponse();
		
		



    	try {
            BeanUtilsBean notNull = new NullAwareBeanUtilsBean();

            notNull.copyProperties(productResponse, product);
            
            // Calculate unit price
           
    		
    		List<ImageDto> img=getImages(product.getImages());
    		productResponse.setImage(img);
    		productResponse.setCategoryPath(product.getCategory().getName());
    		productResponse.setCategoryPath(product.getCategoryPath());
    		productResponse.setTime(product.getUpdatedDate());
    		setCategories(productResponse,product.getCategoryPath());
    		productResponse.calculateOverallRating(product);
    		
    	} catch (IllegalAccessException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	} catch (InvocationTargetException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    return productResponse;
}

	@Override
	public Page<ProductResponse> getProductsByCategory(Pageable pageable, String search, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> searchProducts(String query) {
		Set<String> result=ProductRepo.findByNameContaining(query.toLowerCase());
		return result;
	}

	@Override
	public Set<String> searchCategory(String category, String parentCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> searchCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateProductSeller() {
		// TODO Auto-generated method stub
		
	}


	public Product findProductById(String productId) {
		return ProductRepo.findByProductId(productId).orElse(null);	
	}


	public Response<?> deleteProductImage(Long imageId) {
		Optional<ProductImage> img=imgRepo.findById(imageId);
		if(img==null||!img.isPresent()||img.get()==null) {
			return new Response<Object>(false,"image not found!!");
		}

		try {
			imageUploadService.deleteImage(img.get().getImageUrl());
			return new Response<Object>(true,"image deleted");
			


		}catch(Exception e) {
			return new Response<Object>(false,"Error deleting image from cloud"+" message: "+e.getMessage());
		}
		
		
	}


	public Response<?> uploadProductImage(String productId, MultipartFile file) {
		int nextPriority=1;
		try {
			Product product=ProductRepo.getProductByProductId(productId);
			if(product==null) {
				return new Response<Object>(false,"Product not found!!");
			}
			
			imageUploadDTO imageurl=this.getImageUrl(file);
			if(imageurl==null) {
				return new Response<Object>(false,"Error while uploading image on aws!!");
			}
			
			int priority = extractPriority(file.getOriginalFilename());

	        

	        ProductImage productImage = new ProductImage();
	        productImage.setImageUrl(imageurl.getUrl()); 
	        productImage.setProduct(product); 
	        productImage.setSku(product.getSku());
	        productImage.setName(file.getOriginalFilename());
	        productImage.setSize(file.getSize());
	        productImage.setDimension(imageurl.getHeight()+" x "+imageurl.getWidth());
	        productImage.setPriority( priority == 0 ? nextPriority++ : priority);
	        imgRepo.saveAndFlush(productImage);
	        ImageDto imge=new ImageDto();
	        imge.setDimension(productImage.getDimension());
	        imge.setImageUrl(productImage.getImageUrl());
	        imge.setImageId(productImage.getId());
	        imge.setName(productImage.getName());
	        imge.setPriority(productImage.getPriority());
	        imge.setSize(this.getImageSize(productImage.getSize()));
	        return new Response<>(true,"image uploaded",imge);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Response<>(false,e.getMessage());
		}
	}
	
	public String getImageSize(Long size) {
		double sizeInKB = size / 1024.0;
        String formattedSize = String.format("%.1fKB", sizeInKB);
		return formattedSize;
	}


	@Override
	public SingleImageResponse GetSingleProductImage(List<ProductImage> images) {
    	SingleImageResponse image=new SingleImageResponse();
    	images.sort(Comparator.comparingInt(ProductImage::getPriority));
     images.forEach(e->{
	   if(e.getPriority()==1) {
		image.setImageUrl(e.getImageUrl());
		double sizeInKB = e.getSize() / 1024.0;
        String formattedSize = String.format("%.1fKB", sizeInKB);
		image.setImageSize(formattedSize);
		image.setImageName(e.getName());

	    }else if(e.getPriority()==2) {
	    	image.setImageUrl(e.getImageUrl());
			double sizeInKB = e.getSize() / 1024.0;
	        String formattedSize = String.format("%.1fKB", sizeInKB);
			image.setImageSize(formattedSize);
			image.setImageName(e.getName());
	    }else if(e.getPriority()==3) {

	    	image.setImageUrl(e.getImageUrl());
			double sizeInKB = e.getSize() / 1024.0;
	        String formattedSize = String.format("%.1fKB", sizeInKB);
			image.setImageSize(formattedSize);
			image.setImageName(e.getName());
	    
	    }else if(e.getPriority()==4) {

	    	image.setImageUrl(e.getImageUrl());
			double sizeInKB = e.getSize() / 1024.0;
	        String formattedSize = String.format("%.1fKB", sizeInKB);
			image.setImageSize(formattedSize);
			image.setImageName(e.getName());
	    
	    }else {


	    	image.setImageUrl(e.getImageUrl());
			double sizeInKB = e.getSize() / 1024.0;
	        String formattedSize = String.format("%.1fKB", sizeInKB);
			image.setImageSize(formattedSize);
			image.setImageName(e.getName());
	    
	    
	    }
    });    	 
    	
    	return image;
    	
    }


	public Response<?> addReview(String reviewData, MultipartFile[] files) {
    try {
        // Deserialize review data
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewDto req = objectMapper.readValue(reviewData, ReviewDto.class);

        // Validate request data
        if (req == null || req.getProductId() == null || req.getComment() == null) {
            return new Response<>(false, "Please fill all the required data!", "Missing required data!");
        }

        // Process image uploads
        String[] images = files != null
                ? Arrays.stream(files).map(imageUploadService::uploadImage).toArray(String[]::new)
                : new String[0];

        // Fetch product and user details
        Product product = ProductRepo.findByProductId(req.getProductId()).orElse(null);
        if (product == null) {
            return new Response<>(false, "Product not found!", "Product not found!");
        }
        User user = userService.getUserDetails();

        // Create review
        ProductReview review = new ProductReview();
        review.setStatus(ReviewStatus.PENDING);
        review.setRating(req.getRating());
        review.setDescription(req.getComment());
        review.setReviewerName(user.getFirstname() + " " + user.getLastname());
        review.setUser(user);
        review.setProduct(product);

        // Attach images to review
        if (images.length > 0) review.setImage1(images[0]);
        if (images.length > 1) review.setImage2(images[1]);

        // Add review to product
        product.getReviews().add(review);

        // Update product rating counts
        updateProductRatings(product, req.getRating());

        // Save product with the new review
        ProductRepo.save(product);

        return new Response<>(true, "Review submitted successfully!", "Review submitted");

    } catch (Exception e) {
        e.printStackTrace();
        return new Response<>(false, "Error while saving review", "Error: " + e.getMessage()+" : "+e.getCause());
    }
}

	private void updateProductRatings(Product product, int rating) {
	    switch (rating) {
	        case 1:
	            product.setOneStar(getIncreasedRating(product.getOneStar()));
	            break;
	        case 2:
	            product.setTwoStar(getIncreasedRating(product.getTwoStar()));
	            break;
	        case 3:
	            product.setThreeStar(getIncreasedRating(product.getThreeStar()));
	            break;
	        case 4:
	            product.setFourStar(getIncreasedRating(product.getFourStar()));
	            break;
	        case 5:
	            product.setFiveStar(getIncreasedRating(product.getFiveStar()));
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid rating: " + rating);
	    }
	}

	private void updateDecreaseProductRatings(Product product, int rating) {
	    switch (rating) {
	        case 1:
	            product.setOneStar(getDeccreasedRating(product.getOneStar()));
	            break;
	        case 2:
	            product.setTwoStar(getDeccreasedRating(product.getTwoStar()));
	            break;
	        case 3:
	            product.setThreeStar(getDeccreasedRating(product.getThreeStar()));
	            break;
	        case 4:
	            product.setFourStar(getDeccreasedRating(product.getFourStar()));
	            break;
	        case 5:
	            product.setFiveStar(getDeccreasedRating(product.getFiveStar()));
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid rating: " + rating);
	    }
	}

		private String getIncreasedRating(String old) {
			if(old==null) {
				return "1";
			}else {
				Integer newval=Integer.valueOf(old)+1;
				return newval.toString();
			}
		}
		
		private String getDeccreasedRating(String old) {
			if(old==null) {
				return "1";
			}else {
				Integer newval=Integer.valueOf(old)-1;
				return newval.toString();
			}
		}


		public Response<?> askQuestion(String productId, String question) {
			
			if(productId==null||question==null) {
			return new Response<>(false,"Please fill all the required data!!","Please fill all the required data!!");
		}
		try {
			Product product=ProductRepo.findByProductId(productId).orElse(null);
			User user=userService.getUserDetails();
			if(product==null) {
				return new Response<>(false,"Product not found!!","Product not found!!");
			}
			ProductQuestion review=new ProductQuestion();
			review.setQuestion(question);
			review.setProduct(product);
			review.setUser(user);
			review.setAnswer("Not yet Replied!!");
			review.setStatus(QuestionStatus.NOT_REPLIED);
			review.setUsername(user.getFirstname()+" "+user.getLastname());
			product.getQuestions().add(review);
			ProductRepo.save(product);
			return new Response<>(true,"Question submitted sucessfully!!","Question submitted");
			
		}catch(Exception e) {
			e.printStackTrace();
			return new Response<>(false,"Error while saving review","Error while saving "+e.getCause()+" "+e.getMessage());

		}
	}


		public Response<?> updateReviewStatus(Long reviewId, ReviewStatus status) {
			try {
				reviewRepo.updateReviewStatus(reviewId,status);
				return new Response<>(true,"Review updated","Review "+status.name());

			}catch(Exception e) {
				e.printStackTrace();
				return new Response<>(false,"Error while update",e.getCause()+" : "+e.getMessage());
			}
		
			
		}


		public Response<?> replyQuestion(Long questionId, String message) {
			try {
			ProductQuestion question=questionRepo.findById(questionId).orElse(null);
			if(question==null) {
				return new Response<>(false,"No response Found for the given id: "+questionId,"No question found!!");
			}
				
			question.setAnswer(message);
			question.setStatus(QuestionStatus.REPLIED);
			questionRepo.save(question);
			return new Response<>(true,"Reply updated Sucessfully","Reply Saved!!");
			}catch(Exception e) {
				e.printStackTrace();
				return new Response<>(false,"Error while Replying",e.getCause()+" : "+e.getMessage());
			}
		}


		public Response<?> deleteReview(Long reviewId) {
			try {
				ProductReview review=reviewRepo.findById(reviewId).orElse(null);
				if(review==null) {
					return new Response<>(false,"Reveiew not found","No review found for given id");

				}
				imageUploadService.deleteImage(review.getImage1());
				imageUploadService.deleteImage(review.getImage2());
				updateDecreaseProductRatings(review.getProduct(), review.getRating());
				ProductRepo.save(review.getProduct());
				reviewRepo.delete(review);
				return new Response<>(true,"Review Deleted sucessfully","review deleted");
				
			}catch(Exception e) {
				return new Response<>(false,"error while deleting review","Error: "+e.getCause());
			}

		}


		public Response<?> deleteQuestion(Long id) {
			try {				
				questionRepo.deleteById(id);
				return new Response<>(true,"Question Deleted sucessfully","question deleted!!");
				
			}catch(Exception e) {
				return new Response<>(false,"error while deleting question","Error:"+e.getCause());
			}
		}


		public Response<?> getAdminRviews(String status, Pageable pageable) {
			ReviewStatus stat=null;
			if(status!=null&&status.toLowerCase().contains("approved")) {
				stat=ReviewStatus.APPROVED;
			}else {
				stat=ReviewStatus.PENDING;
			}
			Page<ProductReviewResponse> rrr=reviewRepo.findAllReview(pageable,stat);
			PageResponse<ProductReviewResponse> rr= new PageResponse<>(rrr);
			if(rr==null||rr.getData()==null||rr.getData().isEmpty()) {
				return new Response<>(false,"No reviews found!!","Reviews not available");
			}
			return new Response<>(true,"Review Found!!",rr);
		}


		public Response<?> getAdminQuestions(String status, Pageable pageable) {

			QuestionStatus stat=null;
			if(status!=null&&status.toLowerCase().equalsIgnoreCase("replied")) {
				stat=QuestionStatus.REPLIED;
			}else {
				stat=QuestionStatus.NOT_REPLIED;
			}
			Page<ProductQuestionsResponse> rr=questionRepo.findAllQuestion(pageable,stat);
			PageResponse<ProductQuestionsResponse> rrr= new PageResponse<>(rr);
			if(rrr==null||rrr.getData()==null||rrr.getData().isEmpty()) {
				return new Response<>(false,"No Question found!!","Question not available");
			}
			return new Response<>(true,"Question Found!!",rrr);
		}
		


}
