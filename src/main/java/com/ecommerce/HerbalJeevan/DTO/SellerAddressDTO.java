package com.ecommerce.HerbalJeevan.DTO;

import java.io.Serializable;

public class SellerAddressDTO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8451956696744201458L;
	private String address;
    private String city;
    private String pincode;
    private String phoneNumber;
    private Boolean isDefault;
    private String name;
    private String landmark;
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SellerAddressDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
   
}
