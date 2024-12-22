package com.ecommerce.HerbalJeevan.DTO;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.ecommerce.HerbalJeevan.Enums.AddressType;
import com.ecommerce.HerbalJeevan.Model.UserAddress;
import com.ecommerce.HerbalJeevan.Utility.NullAwareBeanUtilsBean;

public class UserAddressResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6260021117771836227L;
    private String id;

	private String address;
    private String city;
    private String pincode;
    private String phoneNumber;
    private Boolean isDefault;
    private String name;
    private String landmark;
	
	
	public static UserAddressResponse convertToResponseDTO(UserAddress UserAddress) {
		UserAddressResponse responseDTO = new UserAddressResponse();
        NullAwareBeanUtilsBean util=new NullAwareBeanUtilsBean();
        try {
			util.copyProperties(responseDTO, UserAddress);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
        return responseDTO;
    }
	 public static  boolean isAddressTypeChecked(Set<AddressType> addressTypes, AddressType typeToCheck) {
	        return addressTypes != null && addressTypes.contains(typeToCheck);
	    }
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
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	

    // Getters and Setters
}
