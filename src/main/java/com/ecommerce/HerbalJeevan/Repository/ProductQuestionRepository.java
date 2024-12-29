package com.ecommerce.HerbalJeevan.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.HerbalJeevan.DTO.ProductQuestionsResponse;
import com.ecommerce.HerbalJeevan.Enums.QuestionStatus;
import com.ecommerce.HerbalJeevan.Model.ProductQuestion;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long>{

	/*
	 * public ProductQuestionsResponse(Long id, String question, String answer,
	 * QuestionStatus status, String firstname,String lastname,String
	 * productId,LocalDateTime time)
	 */
	@Query("select new com.ecommerce.HerbalJeevan.DTO.ProductQuestionsResponse(q.id,q.question,q.answer,q.status,q.username,p.productId,q.updatedDate) from ProductQuestion q join q.product p where q.status=:status")
	Page<ProductQuestionsResponse> findAllQuestion(Pageable pageable, @Param("status")QuestionStatus stat);

}
