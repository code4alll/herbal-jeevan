package com.ecommerce.HerbalJeevan.DTO;

import java.util.Set;

import com.ecommerce.HerbalJeevan.Enums.AddressType;
import com.ecommerce.HerbalJeevan.Model.UserAddress;

public class UserAddressResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6260021117771836227L;
	private String id;
    private String address;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public UserAddressResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static UserAddressResponse convertToResponseDTO(UserAddress UserAddress) {
		UserAddressResponse responseDTO = new UserAddressResponse();
        responseDTO.setId(UserAddress.getId());
        responseDTO.setAddress(UserAddress.getAddress() != null ? UserAddress.getAddress() : "");
        responseDTO.setSelectedOrigin(UserAddress.getSelectedOrigin() != null ? UserAddress.getSelectedOrigin() : "");
        responseDTO.setCity(UserAddress.getCity() != null ? UserAddress.getCity() : "");
        responseDTO.setArea(UserAddress.getArea() != null ? UserAddress.getArea() : "");
        responseDTO.setStreet(UserAddress.getStreet() != null ? UserAddress.getStreet() : "");
        responseDTO.setOffice(UserAddress.getOffice() != null ? UserAddress.getOffice() : "");
        responseDTO.setPobox(UserAddress.getPobox() != null ? UserAddress.getPobox() : "");
        responseDTO.setPostCode(UserAddress.getPostCode() != null ? UserAddress.getPostCode() : "");
        responseDTO.setPhoneNumber(UserAddress.getPhoneNumber() != null ? UserAddress.getPhoneNumber() : "");
        responseDTO.setSelectedCountry(UserAddress.getSelectedCountry() != null ? UserAddress.getSelectedCountry() : "");
        responseDTO.setAirport(UserAddress.getAirport() != null ? UserAddress.getAirport() : "");
        responseDTO.setSeaport(UserAddress.getSeaport() != null ? UserAddress.getSeaport() : "");

        responseDTO.setIsLocationChecked(isAddressTypeChecked(UserAddress.getAddressTypes(), AddressType.STOCK));
        responseDTO.setIsBillingChecked(isAddressTypeChecked(UserAddress.getAddressTypes(), AddressType.BILLING));
        responseDTO.setIsDefaultChecked(isAddressTypeChecked(UserAddress.getAddressTypes(), AddressType.DEFAULT));

        return responseDTO;
    }
	 public static  boolean isAddressTypeChecked(Set<AddressType> addressTypes, AddressType typeToCheck) {
	        return addressTypes != null && addressTypes.contains(typeToCheck);
	    }

	

    // Getters and Setters
}
