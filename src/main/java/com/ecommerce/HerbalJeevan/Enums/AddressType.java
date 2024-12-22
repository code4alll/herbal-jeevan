package com.ecommerce.HerbalJeevan.Enums;


public enum AddressType {
	    DEFAULT,
	    BILLING,
	    HOME,
	    WORK,	    
	    STOCK;
	
	 public static AddressType fromString(String value) {
	        if (value == null) {
	            throw new IllegalArgumentException("Value must not be null");
	        }

	        try {
	            return AddressType.valueOf(value.toUpperCase());
	        } catch (IllegalArgumentException e) {
	            throw new IllegalArgumentException("No enum constant " + AddressType.class.getCanonicalName() + "." + value, e);
	        }
	    }
	

}

