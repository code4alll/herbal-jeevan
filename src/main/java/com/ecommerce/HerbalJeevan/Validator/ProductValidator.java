package com.ecommerce.HerbalJeevan.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ecommerce.HerbalJeevan.Model.Product;
import com.ecommerce.HerbalJeevan.Validation.ProductValidValues;

public class ProductValidator implements ConstraintValidator<ProductValidValues, Product> {

	@Override
	public boolean isValid(Product value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return true;
	}

}
