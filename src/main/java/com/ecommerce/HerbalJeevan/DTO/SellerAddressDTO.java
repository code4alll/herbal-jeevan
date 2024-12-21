package com.ecommerce.HerbalJeevan.DTO;

import java.io.Serializable;

public class SellerAddressDTO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8451956696744201458L;
	private String address;
    public Boolean getIsLocationChecked() {
		return isLocationChecked;
	}
	public void setIsLocationChecked(Boolean isLocationChecked) {
		this.isLocationChecked = isLocationChecked;
	}
	public Boolean getIsBillingChecked() {
		return isBillingChecked;
	}
	public void setIsBillingChecked(Boolean isBillingChecked) {
		this.isBillingChecked = isBillingChecked;
	}
	public Boolean getIsDefaultChecked() {
		return isDefaultChecked;
	}
	public void setIsDefaultChecked(Boolean isDefaultChecked) {
		this.isDefaultChecked = isDefaultChecked;
	}
	private String selectedOrigin;
    private String city;
    private String area;
    private String street;
    private String office;
    private String pobox;
    private String postCode;
    private String phoneNumber;
    private String selectedCountry;
    private String airport;
    private String seaport;
    private Boolean isLocationChecked;
    private Boolean isBillingChecked;
    private Boolean isDefaultChecked;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSelectedOrigin() {
		return selectedOrigin;
	}
	public void setSelectedOrigin(String selectedOrigin) {
		this.selectedOrigin = selectedOrigin;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getPobox() {
		return pobox;
	}
	public void setPobox(String pobox) {
		this.pobox = pobox;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSelectedCountry() {
		return selectedCountry;
	}
	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	public String getSeaport() {
		return seaport;
	}
	public void setSeaport(String seaport) {
		this.seaport = seaport;
	}
	public SellerAddressDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


    // Getters and Setters
}
