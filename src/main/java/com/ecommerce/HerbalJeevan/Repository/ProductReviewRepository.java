package com.ecommerce.HerbalJeevan.Repository;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.HerbalJeevan.DTO.ProductReviewResponse;
import com.ecommerce.HerbalJeevan.Enums.ReviewStatus;
import com.ecommerce.HerbalJeevan.Model.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>{

	@Transactional
	@Modifying
	@Query("UPDATE  ProductReview t SET t.status=:status  WHERE t.id = :reviewId")
	void updateReviewStatus(@Param("reviewId")Long reviewId, @Param("status")ReviewStatus status);
	
	


	/*
	 * ProductReviewResponse(Long id, String productId, int rating, String title,
	 * String description, Date time, ReviewStatus status, String reviewerName,
	 * String image1, String image2)
	 */
   @Query("select new com.ecommerce.HerbalJeevan.DTO.ProductReviewResponse(r.id,p.productId,r.rating,r.title,r.description,r.updatedAt,r.status,r.reviewerName,r.image1,r.image2) from ProductReview r join r.product p where r.status=:status")
	Page<ProductReviewResponse> findAllReview(Pageable pageable, @Param("status")ReviewStatus stat);

}
