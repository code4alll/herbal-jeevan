package com.ecommerce.HerbalJeevan.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.HerbalJeevan.DTO.RegisterDto;
import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Service.UserService;
import com.ecommerce.HerbalJeevan.Utility.Response;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/user/signup")
	private ResponseEntity<Response<?>> RegisterUser(@RequestBody RegisterDto user){
		return ResponseEntity.ok(userService.registerUser(user, Roles.USER));
		
	}

}
