package com.ecommerce.HerbalJeevan.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.HerbalJeevan.DTO.SingleProductDTO;
import com.ecommerce.HerbalJeevan.Model.Category;
import com.ecommerce.HerbalJeevan.Model.Product;

public interface ProductRepository extends JpaRepository<Product,String> {

	Product getProductByProductId(String productId);

//	@Query("Select p from Product p left join fetch p.category c where c.name=:categoryName")
//	List<Product> findByCategoryName(@Param(value = "categoryName") String categoryName);

	 Page<Product> findByNameContainingIgnoreCase(String search, Pageable pageable);
	 
//	 @Query("SELECT p FROM Product p WHERE LEVENSHTEIN_LOWER(p.productName, :search) <= 2")
//	    Page<Product> findSimilarProducts(@Param("search") String search, Pageable pageable);
	 
//	 @Query( "SELECT * FROM Product p WHERE REGEXP_REPLACE(LOWER(p.productName), '[^a-zA-Z0-9]', '') LIKE %:name%")
//	    Page<Product> findByNameContainingIgnoreSpecialCharacters(@Param("name") String name, Pageable pageable);	 
//	 
//	 @Query("SELECT p FROM Product p WHERE lower(p.name)  LIKE %:search%")
//	    Page<Product> findMatchingProducts(@Param("search") String search, Pageable pageable);
//	 
//	 
//	 
//	 
//	 @Query("SELECT p FROM Product p join  p.category c WHERE lower(c.urlSlug) like lower(concat('%', :category, '%'))")
//	 Page<Product> findWithCategoryAndSearch(@Param("category") String category, @Param("search") String search, Pageable pageable);
//	
//	 @Query("SELECT p FROM Product p join  p.category c WHERE lower(c.urlSlug) like lower(concat('%', :category, '%'))")
//	 Page<Product> findWithCategory(@Param("category") String category, Pageable pageable);
//	 
//	 @Query("SELECT DISTINCT new com.Ulink.Ecommerce.DTO.SellerProductDto(p.productName, p.visibility, p.availability, p.productId, " +
//		       "p.updatedAt, p.status, c.name, p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision " +
////		       "i.size, i.imageUrl, i.name"
//		       ") " +
//		       "FROM Product p JOIN p.category c"
////		       + " c LEFT JOIN p.images i " +
//		       +" WHERE p.addedBy = :username " +
////		       "AND i.priority = (SELECT MIN(i3.priority) FROM p.images i3 WHERE i3.product = p) "+
////		       "AND i.id = (SELECT MIN(i2.id) FROM p.images i2 WHERE i2.product = p) " +
//		       "GROUP BY p.productName, p.visibility, p.availability, p.productId, p.updatedAt, p.status, c.name, " +
//		       "p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision"
////		       + ", i.size, i.imageUrl, i.name"
//		       )
//		Page<SellerProductDto> findSellerProducts(@Param("username") String username, Pageable pageable);
//
//	 
//    @Query("SELECT p from Product p left join fetch p.seller where p.productId= :productId  ")
//	Optional<Product> findProductByProductId(@Param("productId") String productId);
    
//    @Query("SELECT new com.Ulink.Ecommerce.DTO.SellerProductDto(p.productName, p.visibility, p.availability, p.productId, " +
//    	       "p.updatedAt, p.status, c.name, p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision " +
////    	       "i.size, i.imageUrl, i.name"
//    	        ") " +
//    	       "FROM Product p JOIN p.category c "
////    	       + "LEFT JOIN p.images i " +
//    	       +"WHERE p.addedBy = :username  " +
////		       "AND i.priority = (SELECT MIN(i3.priority) FROM p.images i3 WHERE i3.product = p) "+
//    	       " AND (LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
//    	       "LOWER(p.keyWords) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
//    	       "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
//    	       "GROUP BY p.productName, p.visibility, p.availability, p.productId, p.updatedAt, p.status, c.name, " +
//    	       "p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision"
////    	       + " i.size, i.imageUrl, i.name"
//    	       + "")
//	Page<SellerProductDto> findSellerProducts(@Param("username") String username, Pageable pageable,@Param("search") String search);
	
	 
//	 @Query("SELECT new com.Ulink.Ecommerce.DTO.SellerProductDto(p.productName, p.visibility, p.availability, p.productId, " +
//	           "p.updatedAt, p.status, c.name, p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision, " +
//	           "i.size, i.imageUrl, i.name "
//	            +") " +
//	           "FROM Product p JOIN p.category c "
//	           + "LEFT JOIN p.images i " +
//	           "WHERE p.productId = :productId " +
//	           "GROUP BY p.productName, p.visibility, p.availability, p.productId, p.updatedAt, p.status, c.name, " +
//	           "p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision,"
//	           + " i.size, i.imageUrl, i.name"
//	           + "")
//	SellerProductDto findSellerProductsById(@Param("productId") String productId);

	    @Query("SELECT p FROM Product p WHERE p.category IN :categories")
	    Page<Product> findByCategories(@Param("categories") List<Category> categories,Pageable page);
	    
	    @Query("SELECT p.name FROM Product p WHERE lower(p.name) LIKE %:name% or lower(p.category.name) like %:name%")
	    Set<String> findByNameContaining(@Param("name") String name);

	    
	    @Query("SELECT p FROM Product p join  p.category c WHERE " +
	            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	            "LOWER(p.info) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	            "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
	            " AND c in (:category) ")
		Page<Product> findByCategoriesAndSearchterm(@Param("category")List<Category> categories, @Param("search") String search, Pageable pageable);
	    
	    @Query("SELECT p FROM Product p join  p.category c WHERE " +
	            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	           	            "LOWER(p.info) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	            "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
	            "")
	    Page<Product> findSimilarProducts(@Param("search") String search, Pageable pageable);

	    
	    
	    @Query("SELECT COUNT(p) FROM Product p join p.category c WHERE p.seller.email = :username AND c IN (:category)")
	    Long countBySellerNameAndCategories(@Param("username") String user, @Param("category")List<Category> category);
	  
	    @Query("SELECT COUNT(p) FROM Product p  WHERE p.seller.email = :username ")
	    Long countBySellerName(@Param("username") String user);

	    
//	    @Query("SELECT DISTINCT new com.Ulink.Ecommerce.DTO.SellerProductDto(p.productName, p.visibility, p.availability, p.productId, " +
//			       "p.updatedAt, p.status, c.name, p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision " +
//			       ") " +
//			       "FROM Product p JOIN p.category c " +
//			       "WHERE p.addedBy = :username and p.category in :categories " +
////			       "AND i.priority = (SELECT MIN(i3.priority) FROM p.images i3 WHERE i3.product = p) "+
////			       "AND i.id = (SELECT MIN(i2.id) FROM p.images i2 WHERE i2.product = p) " +
//			       "GROUP BY p.productName, p.visibility, p.availability, p.productId, p.updatedAt, p.status, c.name, " +
//			       "p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision ")
//		Page<SellerProductDto> findSellerProductByCategories(@Param("categories") List<Category> categories, Pageable pageable, @Param("username") String user);

//	    @Query("SELECT new com.Ulink.Ecommerce.DTO.SellerProductDto(p.productName, p.visibility, p.availability, p.productId, " +
//	    	       "p.updatedAt, p.status, c.name, p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision )" +
////	    	       "i.size, i.imageUrl, i.name)"
//	    	        " " +
//	    	       "FROM Product p JOIN p.category c "
////	    	       + "LEFT JOIN p.images i " +
//	    	       +"WHERE p.addedBy = :username AND p.category IN :categories  " +
////			       "AND i.priority = (SELECT MIN(i3.priority) FROM p.images i3 WHERE i3.product = p) "+
//
//	    	       " and (LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
//	    	       "LOWER(p.keyWords) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
//	    	       "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
//	    	       "GROUP BY p.productName, p.visibility, p.availability, p.productId, p.updatedAt, p.status, c.name, " +
//	    	       "p.minOrderQuant, p.sellPrice, p.unitPrice, c.gst, c.commision"
////	    	       + " i.size, i.imageUrl, i.name"
//	    	       + "")
//		Page<SellerProductDto> findSellerProductByCategoriesAndSearchterm(@Param("categories")List<Category> categories, @Param("search") String search,
//				Pageable pageable,@Param("username") String user);

	    
	    @Query("select productId from Product")
		List<String> getAllProductId();

		Optional<Product> findByProductId(String productId);

	    @Query("SELECT p from Product p left join fetch p.seller where p.productId= :productId  ")
		Optional<Product> findProductByProductId(@Param("productId") String productId);

}


