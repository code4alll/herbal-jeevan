package com.ecommerce.HerbalJeevan.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.HerbalJeevan.DTO.RegisterDto;
import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Model.Admin;
import com.ecommerce.HerbalJeevan.Model.User;
import com.ecommerce.HerbalJeevan.Model.UserModel;
import com.ecommerce.HerbalJeevan.Repository.UserRepo;
import com.ecommerce.HerbalJeevan.Utility.Response;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Response<?> registerUser(RegisterDto user, Roles role) {
	    // Create a validator only once
	    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();

	    // Initialize error map
	    Map<String, String> errorMap = new HashMap<>();

	    try {
	        // Create user entity based on role
	        UserModel userEntity = (role.equals(Roles.USER)) ? 
	            new User(user.getEmail(), user.getPassword(), user.getEmail(), user.getFirstname(), user.getLastname(), null) :
	            new Admin(user.getEmail(), user.getPassword(), user.getEmail(), user.getFirstname(), user.getLastname(), null);

	        userEntity.setRole(role);

	        // Check if email already exists
	        if (isEmailAlreadyRegistered(userEntity.getEmail(), userEntity.getRole())) {
	            errorMap.put("email", "Email is already registered");
	            return new Response<>(false, "Error while saving", errorMap);
	        }

	        // Validate user entity
	        Set<ConstraintViolation<UserModel>> violations = validator.validate(userEntity);
	        if (!violations.isEmpty()) {
	            // Collect validation errors
	            for (ConstraintViolation<UserModel> violate : violations) {
	                errorMap.put(violate.getPropertyPath().toString(), violate.getMessage());
	            }
	            return new Response<>(false, "Validation errors", errorMap);
	        }

	        // Encode password and save user entity
	        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
	        userRepo.save(userEntity);

	        return new Response<>(true, "User created successfully", "User saved");

	    } catch (Exception e) {
	        // Log and handle unexpected errors
	        e.printStackTrace();
	        return new Response<>(false, "An unexpected error occurred", null);
	    }
	}

	
	 public boolean isEmailAlreadyRegistered(String email,Roles role) {
	    	
         return (userRepo.existsByEmailAndRole(email,role)||userRepo.existsByUsernameAndRole(email, role));
    }
	
}
