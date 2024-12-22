package com.ecommerce.HerbalJeevan.Service;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.HerbalJeevan.DTO.FileDetailsVo;
import com.ecommerce.HerbalJeevan.DTO.ImageDto;
import com.ecommerce.HerbalJeevan.DTO.ProductFilterDTO;
import com.ecommerce.HerbalJeevan.DTO.ProductImageDTO;
import com.ecommerce.HerbalJeevan.DTO.ProductResponse;
import com.ecommerce.HerbalJeevan.DTO.SingleImageResponse;
import com.ecommerce.HerbalJeevan.DTO.SingleProductDTO;
import com.ecommerce.HerbalJeevan.DTO.imageUploadDTO;
import com.ecommerce.HerbalJeevan.DTO.productdto;
import com.ecommerce.HerbalJeevan.Enums.SortOption;
import com.ecommerce.HerbalJeevan.Model.Admin;
import com.ecommerce.HerbalJeevan.Model.Product;
import com.ecommerce.HerbalJeevan.Model.ProductImage;



public interface ProductService {
	boolean addProduct(productdto productDTO, List<ProductImageDTO> imageDTO, Admin user) throws IOException;
    void deleteProductWithImages(String id);
    String ImageEncodeDecode(String filename);
    String deleteProduct(Long id);
    FileDetailsVo saveProductBulkUploadData(Map<Integer, Product> dataMap,String user);
    public <T extends Categorizable > void setCategories(T productResponse, String categoryPath);
    Optional<SingleProductDTO> getProductById(String productId);
    List<ProductImageDTO> getImageDto(MultipartFile[] file);
    void getImageList(List<ProductImageDTO> imageDTOs, Product product, List<String> savedImagePaths, List<ProductImage> productImages) throws IOException;
    String encodeImage(String imagePath);
    String decodeBase64ToImage(String base64Image, String outputImagePath);
    Double CalculateUnitPrice(Product product);
    Double CalculateSellPrice(Product product);
    Double CalculateUnitPriceCart(Product product);
    Double CalculateSellPriceCart(Product product);
    Page<ProductResponse> getAllProductsWithImages(Pageable pageable, String search, SortOption sort);
    Page<ProductResponse> getAllProductsWithImages(Pageable pageable, String search,String category, ProductFilterDTO filter,SortOption sort);
    public com.ecommerce.HerbalJeevan.DTO.ImageResource fetchImage(String filename) throws Exception;
    boolean updateProduct(productdto res, List<ProductImageDTO> productImage, Product oldProduct);
    void deleteImage(String fileName);
    imageUploadDTO getImageUrl(MultipartFile imageFile) throws IOException;
    Product convertToProductEntity(productdto productDTO);
    List<ImageDto> getImages(List<ProductImage> image);
    ProductResponse mapToProductResponse(Product product, String country);
	Page<ProductResponse> getProductsByCategory(Pageable pageable, String search, String category);
//	PageResponse<SellerProductDto> GetSellerProducts(Pageable pageable, String search,String category);
//	Optional<SellerProductDto> updateProductById(SellerProductDto productData);
//	SingleImageResponse GetSingleProductImage(List<ProductImage> images);
//	ProductUpdateResponse getProductToupdate(String productId);
//	ResponseDto<?> updateProduct(String productId, ProductDTO product);
//	ResponseDto<?> deleteProductImage(Long imageId);
//	ResponseDto<?> uploadProductImage(String productId, MultipartFile file);
	Set<String> searchProducts(String query);
	Set<String> searchCategory(String category, String parentCategory);
	Set<String> searchCategory(String category);
	void updateProductSeller();
	void deleteProductWithImagesAndPriceList(String id);
	SingleImageResponse GetSingleProductImage(List<ProductImage> images);

}

