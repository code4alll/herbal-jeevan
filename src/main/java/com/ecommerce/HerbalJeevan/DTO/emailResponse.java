package com.ecommerce.HerbalJeevan.DTO;


public class emailResponse {
	
	private String message;
	private boolean success;
	private String email;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public emailResponse() {
		super();
	}
	
	

}
