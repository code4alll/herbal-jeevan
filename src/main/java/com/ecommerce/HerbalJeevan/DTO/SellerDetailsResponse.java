package com.ecommerce.HerbalJeevan.DTO;

import com.ecommerce.HerbalJeevan.Enums.Status;

public class SellerDetailsResponse {
	private String name;
	private String countryOfoperation;
	private String companyName;
	private String isVerified;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountryOfoperation() {
		return countryOfoperation;
	}
	public void setCountryOfoperation(String countryOfoperation) {
		this.countryOfoperation = countryOfoperation;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Status status) {
		if(status.equals(Status.ACTIVE)) {
			this.isVerified="VERIFIED";
		}else {
		this.isVerified = "UNVERIFIED";}
	}
	
}
