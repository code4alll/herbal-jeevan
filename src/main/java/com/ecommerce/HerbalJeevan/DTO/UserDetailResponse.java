package com.ecommerce.HerbalJeevan.DTO;

import javax.persistence.Column;

import com.ecommerce.HerbalJeevan.Model.User;

public class UserDetailResponse {
	     private String userId;
	
	   
	    
	    @Column(name="email")
	    private String email;

	    
	    @Column(name="firstname")
	    private String firstname;
	    @Column(name="lastname")
	    private String lastname;
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public UserDetailResponse() {
			super();
			// TODO Auto-generated constructor stub
		}
	    public UserDetailResponse(User u) {
	    	this.setEmail(u.getEmail());
	    	this.setFirstname(u.getFirstname());
	    	this.setLastname(u.getLastname());
	    	this.setUserId(u.getUserId());
	    }
		public UserDetailResponse(String userId,  String firstname, String lastname,String email) {
			super();
			this.userId = userId;
			this.email = email;
			this.firstname = firstname;
			this.lastname = lastname;
		}
	    

}
