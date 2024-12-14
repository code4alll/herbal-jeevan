package com.ecommerce.HerbalJeevan.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.HerbalJeevan.Config.SecurityConfig.ClaimedToken;
import com.ecommerce.HerbalJeevan.DTO.FileDetailsVo;
import com.ecommerce.HerbalJeevan.DTO.ImageDto;
import com.ecommerce.HerbalJeevan.DTO.ImageResource;
import com.ecommerce.HerbalJeevan.DTO.ProductFilterDTO;
import com.ecommerce.HerbalJeevan.DTO.ProductImageDTO;
import com.ecommerce.HerbalJeevan.DTO.ProductResponse;
import com.ecommerce.HerbalJeevan.DTO.SingleProductDTO;
import com.ecommerce.HerbalJeevan.DTO.imageUploadDTO;
import com.ecommerce.HerbalJeevan.DTO.productdto;
import com.ecommerce.HerbalJeevan.Enums.SortOption;
import com.ecommerce.HerbalJeevan.Model.Admin;
import com.ecommerce.HerbalJeevan.Model.Category;
import com.ecommerce.HerbalJeevan.Model.Product;
import com.ecommerce.HerbalJeevan.Model.ProductImage;
import com.ecommerce.HerbalJeevan.Repository.CategoryRepository;
import com.ecommerce.HerbalJeevan.Repository.ImageRepository;
import com.ecommerce.HerbalJeevan.Repository.ProductRepository;
import com.ecommerce.HerbalJeevan.Utility.IdGeneratorUtils;
import com.ecommerce.HerbalJeevan.Utility.NullAwareBeanUtilsBean;


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
	public void deleteProductWithImagesAndPriceList(Long id) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return Optional.empty();
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
	public Page<ProductResponse> getAllProductsWithImages(Pageable pageable, String search, String category,
			ProductFilterDTO filter, SortOption sort) {
        Page<Product> products;
        Long total=0l;
        if (StringUtils.isNotBlank(search)) {
            String cleanedQuery = IdGenerator.cleanString(search);

//            products = ProductRepo.findByProductNameContainingIgnoreCase(search, pageable);
            products = ProductRepo.findSimilarProducts( search.toLowerCase(),  pageable);
            total=products.getTotalElements();
        }else {
            products = ProductRepo.findAll(pageable);
            total=products.getTotalElements();

        }
        
        final String country;
        
		String username=null;
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if(authentication.isAuthenticated()&&!authentication.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
	    	
	    	ClaimedToken user=userService.getAuthenticatedUser(authentication.getPrincipal());
	    	country=user.getCountry();
	    	username=user.getUsername();
	    	
	    	
	    }else {
	    	country="india";
	    }

	    List<ProductResponse> sortedProducts = products.stream()
                .map(product -> mapToProductResponse(product, country))
                .sorted(getComparator(sort))
                .collect(Collectors.toList());

        
        return new PageImpl<>(sortedProducts, pageable, total);
        
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
		// TODO Auto-generated method stub
		return false;
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
    		productResponse.setImages(img);
    		productResponse.setCategoryPath(product.getCategory().getName());
    		productResponse.setCategoryPath(product.getCategoryPath());
    		productResponse.setTime(product.getUpdatedDate());
    		setCategories(productResponse,product.getCategoryPath());
    		
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
		// TODO Auto-generated method stub
		return null;
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

}
