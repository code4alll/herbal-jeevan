package com.ecommerce.HerbalJeevan.Model;



import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.ecommerce.HerbalJeevan.Enums.AddressType;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class UserAddress {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

	private String address;
    private String city;
    private String pincode;
    private String phoneNumber;
    private Boolean isDefault;
    private String name;
    private String landmark;

    @ElementCollection(targetClass = AddressType.class)
    @CollectionTable(name = "address_type")
    @Enumerated(EnumType.STRING)
    private Set<AddressType> addressTypes;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @JsonBackReference
    private UserModel userId;

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

	public Set<AddressType> getAddressTypes() {
		return addressTypes;
	}

	public void setAddressTypes(Set<AddressType> addressTypes) {
		this.addressTypes = addressTypes;
	}

	public UserModel getUserId() {
		return userId;
	}

	public void setUserId(UserModel userId) {
		this.userId = userId;
	}


   
}

