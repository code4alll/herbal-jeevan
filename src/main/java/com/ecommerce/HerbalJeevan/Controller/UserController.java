package com.ecommerce.HerbalJeevan.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.HerbalJeevan.DTO.LoginDto;
import com.ecommerce.HerbalJeevan.DTO.LoginResponse;
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
	
	@GetMapping("/user/Login")
	private  ResponseEntity<?> LoginUser (@RequestBody LoginDto user){
		LoginResponse response=userService.LoginData(user,Roles.USER);
		Response<?> res=new Response<>();
		
		
	    
	    if(response.getToken()!=null&&response.getStatus()) {
	    	
	    	return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
	    else if(response.getMessage()!=null&&response.getToken()==null) {
	    	res=new Response<String>(false,response.getMessage(),response.getMessage());
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	    }
	    else {
	    	res=new Response<String>(false,"Server Unavailable","Error while fetching User Information!!");
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	    }
	
}

}
	
