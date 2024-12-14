package com.ecommerce.HerbalJeevan.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.HerbalJeevan.Model.ProductImage;

public interface ImageRepository extends JpaRepository<ProductImage,Long>{
	
	@Query("select i from ProductImage i join i.product p where p.productId in (:product) ")
	List<ProductImage> getProductImage(@Param("product") Set<String> product);
	

}
