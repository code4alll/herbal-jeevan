package com.ecommerce.HerbalJeevan.DTO;

import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Model.Admin;
import com.ecommerce.HerbalJeevan.Model.User;

public class TotalOrderResponse {
	private String id;
	private String name;
	private String username;
	private Long noOfOrders;
	private Roles userType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getNoOfOrders() {
		return noOfOrders;
	}
	public void setNoOfOrders(Long noOfOrders) {
		this.noOfOrders = noOfOrders;
	}
	public Roles getUserType() {
		return userType;
	}
	public void setUserType(Roles userType) {
		this.userType = userType;
	}
	public TotalOrderResponse(User b) {
		super();
		this.id = b.getUserId();
		this.name = b.getFirstname()+" "+b.getLastname();
		this.username = b.getUsername();
		this.noOfOrders = 0L;
		this.userType = Roles.USER;
	}
	
	public TotalOrderResponse(Admin b) {
		super();
		this.id = b.getUserId();
		this.name = b.getFirstname()+" "+b.getLastname();
		this.username = b.getUsername();
		this.noOfOrders = 1L;
		this.userType = Roles.ADMIN;
	}
	public TotalOrderResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public TotalOrderResponse increateNoOfOrder() {
		this.noOfOrders+=1;
		return this;
	}
	
	


}
