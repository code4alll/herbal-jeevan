package com.ecommerce.HerbalJeevan.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ecommerce.HerbalJeevan.Model.UserModel;
import com.ecommerce.HerbalJeevan.Validation.UserValidValues;

public class UserValidator implements ConstraintValidator<UserValidValues, UserModel>{

	@Override
	public boolean isValid(UserModel value, ConstraintValidatorContext context) {
		boolean name=validateName(context,value);
		boolean email=validateEmail(context,value);
//		boolean number=validateNumber(context,value);
		boolean password=validatePassword(context,value);
		
		if((name&&email&&password)||(value.getFlag()!=null&&value.getFlag())) {
			return true;
		}
		return false;
	}



	    private boolean validatePassword(ConstraintValidatorContext context, UserModel value) {
	        if (value.getPassword() == null || value.getPassword().isEmpty()) {
	            context.disableDefaultConstraintViolation();
	            context.buildConstraintViolationWithTemplate("Password is mandatory").addConstraintViolation();
	            return false;
	        }
	        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	        if (!value.getPassword().matches(passwordRegex)) {
	            context.disableDefaultConstraintViolation();
	            context.buildConstraintViolationWithTemplate("Password must be at least 8 characters long, include an uppercase letter, a number, and a special character.").addPropertyNode("password")
	                    .addConstraintViolation();
	            return false;
	        }
	        return true;
	    }

//	    private boolean validateNumber(ConstraintValidatorContext context, UserModel value) {
//	        try {
//	            if (value.get() == null || value.getPhoneNumber().isEmpty()) {
//	                context.disableDefaultConstraintViolation();
//	                context.buildConstraintViolationWithTemplate("Phone number is mandatory").addConstraintViolation();
//	                return false;
//	            }
//	            Long.parseLong(value.getPhoneNumber()); // Ensures it's numeric
//	        } catch (NumberFormatException e) {
//	            context.disableDefaultConstraintViolation();
//	            context.buildConstraintViolationWithTemplate("Phone number must be numeric").addConstraintViolation();
//	            return false;
//	        }
//	        return true;
//	    }

	    private boolean validateEmail(ConstraintValidatorContext context, UserModel value) {
	        if (value.getEmail() == null || value.getEmail().isEmpty()) {
	            context.disableDefaultConstraintViolation();
	            context.buildConstraintViolationWithTemplate("Email is mandatory").addConstraintViolation();
	            return false;
	        }
	        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	        if(value.getEmail().equalsIgnoreCase("Already existed")) {
	        	context.disableDefaultConstraintViolation();
	            context.buildConstraintViolationWithTemplate("Email Already existed").addPropertyNode("email").addConstraintViolation();
	            return false;
	        }
	        if (!value.getEmail().matches(emailRegex)) {
	            context.disableDefaultConstraintViolation();
	            context.buildConstraintViolationWithTemplate("Email is not valid").addPropertyNode("email").addConstraintViolation();
	            return false;
	        }
	        return true;
	    }

	    private boolean validateName(ConstraintValidatorContext context, UserModel value) {
	        if (value.getFirstname() == null ||value.getFirstname() == null) {
	            context.disableDefaultConstraintViolation();
	            context.buildConstraintViolationWithTemplate("Name is mandatory").addPropertyNode("email").addConstraintViolation();
	            return false;
	        }
	        String nameRegex = "^[A-Za-z\\s]+$"; // Only alphabets and spaces allowed
	        if (!value.getFirstname().matches(nameRegex)||!value.getLastname().matches(nameRegex)) {
	            context.disableDefaultConstraintViolation();
	            context.buildConstraintViolationWithTemplate("Name must only contain alphabets").addPropertyNode("firstname").addConstraintViolation();
	            return false;
	        }
	        return true;
	    }
	

}

