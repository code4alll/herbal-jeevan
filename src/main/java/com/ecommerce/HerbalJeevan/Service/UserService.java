package com.ecommerce.HerbalJeevan.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.catalina.Manager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.HerbalJeevan.Config.SecurityConfig.JwtBlacklistService;
import com.ecommerce.HerbalJeevan.Config.SecurityConfig.JwtTokenUtil;
import com.ecommerce.HerbalJeevan.DTO.AddressResponseDTO;
import com.ecommerce.HerbalJeevan.DTO.LoginDto;
import com.ecommerce.HerbalJeevan.DTO.LoginResponse;
import com.ecommerce.HerbalJeevan.DTO.RegisterDto;
import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Model.Admin;
import com.ecommerce.HerbalJeevan.Model.User;
import com.ecommerce.HerbalJeevan.Model.UserAddress;
import com.ecommerce.HerbalJeevan.Model.UserModel;
import com.ecommerce.HerbalJeevan.Repository.UserRepo;
import com.ecommerce.HerbalJeevan.Utility.Response;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 @Autowired
	    JwtTokenUtil jwtTokenProvider;
	    
	    @Autowired
	    private JwtBlacklistService jwtBlacklistService;

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
	        userEntity.setFlag(true);
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


	
	

	public LoginResponse LoginData(LoginDto loginDto,Roles role) {
	    try {
	        // Validate input fields
	        if (loginDto.getUsername() == null) {
	            return new LoginResponse(loginDto.getUsername(), null, false, "Login Failed, Please Enter All required fields (username, password)", null, null, null, null);
	        }

	        if (loginDto.getPassword() == null) {
	            return new LoginResponse(loginDto.getUsername(), null, false, "Login Failed, Please Enter password", null, null, null, null);
	        }

	    

	        // Get role

	        // Find user by username and role
	        UserModel user = userRepo.findByUsernameAndRole(loginDto.getUsername(), role).orElse(null);

	        // Check if user exists and password matches
	        if (user != null) {
	            String password = loginDto.getPassword();
	            String encodedPassword = user.getPassword();
	            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);

	            if (isPwdRight) {
	                // Generate token
	                String token = jwtTokenProvider.generateToken(user);

	                // Initialize the response object
	                LoginResponse res = new LoginResponse();
	                res.setToken(token);
	                res.setStatus(true);
	                res.setMessage("Login Success");
	   
	                res.setFirstname(user.getFirstname());
	                res.setLastname(user.getLastname());
	                res.setEmail(user.getEmail());
	                res.setRole(role);
	               

	             

	                return res;

	            } else {
	                return new LoginResponse(loginDto.getUsername(), null, false, "Invalid Credentials", null, null, null, null);
	            }

	        } else {
	            return new LoginResponse(loginDto.getUsername(), null, false, "User does not exist", null, null, null, null);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new LoginResponse(loginDto.getUsername(), null, false, "An unexpected error occurred", null, null, null, null);
	    }
	}


	
}
