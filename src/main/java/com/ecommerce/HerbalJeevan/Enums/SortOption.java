package com.ecommerce.HerbalJeevan.Enums;

public enum SortOption {
	
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    RATING,
    SALES,
    NEWEST,
    RELEVANCE;

 public static SortOption fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null");
        }

        try {
            return SortOption.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No enum constant " + SortOption.class.getCanonicalName() + "." + value, e);
        }
    }
}
