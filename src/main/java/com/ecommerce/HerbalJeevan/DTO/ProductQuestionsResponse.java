package com.ecommerce.HerbalJeevan.DTO;

import java.time.LocalDateTime;

import com.ecommerce.HerbalJeevan.Enums.QuestionStatus;
import com.ecommerce.HerbalJeevan.Model.ProductQuestion;

public class ProductQuestionsResponse {
	
	private Long id;
	private String question;
	private String answer;
	private QuestionStatus status;
	private String username;
	private String productId;
	private LocalDateTime time;
	public ProductQuestionsResponse(Long id, String question, String answer, QuestionStatus status, String username,
			String productId,LocalDateTime time) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.status = status;
		this.username = username;
		this.productId = productId;
		this.time=time;
	}
	
	public ProductQuestionsResponse(ProductQuestion q) {
		super();
		this.id=q.getId();
		this.question=q.getQuestion();
		this.answer=q.getAnswer();
		this.status=q.getStatus();
		this.username=q.getUsername();
		this.productId=q.getProduct().getProductId();
		this.time=q.getUpdatedDate();
	
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public QuestionStatus getStatus() {
		return status;
	}
	public void setStatus(QuestionStatus status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	
	

}
