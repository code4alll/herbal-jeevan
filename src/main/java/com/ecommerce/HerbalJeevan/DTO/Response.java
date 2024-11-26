package com.ecommerce.HerbalJeevan.DTO;

public class Response<T> {
	
	private Boolean  status;
	private String message;
    private T data;
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Response(Boolean status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public Response(Boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
    
    
}
