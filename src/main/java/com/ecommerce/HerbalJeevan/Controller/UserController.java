package com.ecommerce.HerbalJeevan.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.HerbalJeevan.DTO.LoginDto;
import com.ecommerce.HerbalJeevan.DTO.LoginResponse;
import com.ecommerce.HerbalJeevan.DTO.RegisterDto;
import com.ecommerce.HerbalJeevan.DTO.SellerAddressDTO;
import com.ecommerce.HerbalJeevan.DTO.UserAddressResponse;
import com.ecommerce.HerbalJeevan.DTO.emailResponse;
import com.ecommerce.HerbalJeevan.Enums.OtpVerificationStatus;
import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Service.OTPservices;
import com.ecommerce.HerbalJeevan.Service.UserService;
import com.ecommerce.HerbalJeevan.Utility.Response;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/user/signup")
	private ResponseEntity<Response<?>> RegisterUser(@RequestBody RegisterDto user){
		return ResponseEntity.ok(userService.registerUser(user, Roles.USER));
		
	}
	
	@PostMapping("/user/Login")
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
	
	
	
	

	
	 @PostMapping("/verifyOtp")
	    private ResponseEntity<?> verifyOTP(@RequestParam("username") String username,@RequestParam("otp") String otp,@RequestParam("role") String role){
	    	OtpVerificationStatus response=null;
	    	emailResponse res=new emailResponse();
	    	if(username!=null&&otp!=null&role!=null) {
	    		
	    		try {
	    			response=OTPservices.verifyOtp(username, otp);
	    			if(response.equals(OtpVerificationStatus.EXPIRED)) {
	    				
	    				res.setEmail(username);
	    				res.setMessage(response.toString());
	    				res.setSuccess(false);
	    	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

	    			}
	    			else if(response.equals(OtpVerificationStatus.VERIFIED)) {
	    				res.setEmail(username);
	    				res.setSuccess(false);
	    				
	    				if(role!=null&&role.equalsIgnoreCase("USER")) {
	    					if(userService.verifyUser(username)) {
	    						res.setMessage(response.toString());
	    						res.setSuccess(true);
	        	            return ResponseEntity.status(HttpStatus.OK).body(res);}

	    				}else if(role!=null&&role.equalsIgnoreCase("ADMIN")) {
	    					if(userService.verifyAdmin(username)) {
	    						res.setMessage(response.toString());
	    						res.setSuccess(true);
	        	            return ResponseEntity.status(HttpStatus.OK).body(res);}

	    				}
	    				
	    	            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);

	    			

	    			}
	    			else {
	    				res.setEmail(username);
	    				res.setMessage(response.toString());
	    				res.setSuccess(false);
	    	            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);

	    			}
	    			
	    		}catch(Exception e) {
	    			res.setEmail(username);
					res.setMessage("server error");
					res.setSuccess(false);
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);

	    		}
	    	}else {
	    		res.setEmail(username);
				res.setMessage("please check username or otp");
				res.setSuccess(false);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);

	    	}
		
	    }
	 
	 @PostMapping("/logout")
	    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
	        if (token.startsWith("Bearer ")) {
	            token = token.substring(7);
	            userService.logoutUser(token);

	        } 
	        return ResponseEntity.ok("Successfully logged out");
	    }
	 
	 
	 @PostMapping("/user/forgot-password")
		private ResponseEntity<?> UpdatePassword(@RequestBody LoginDto updateDetails){
			Response<Object> res=new Response<>();
			if(updateDetails==null) {
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(false,"please enter required information"));
			}
			
			res=userService.ForgotPassword(updateDetails,Roles.USER);
			return ResponseEntity.ok(res);
		}
	 
	 @PostMapping("/forgot-password/verify")
	 private ResponseEntity<?> VerifyOtpForPassword(@RequestParam(name="otp",required=true) String otp,@RequestParam(name="username",required=true)String username,@RequestParam(name="role") Roles role){
			 
		 Response response=userService.VerifyAndUpdatePassword(otp,username,role);
		 
		 return ResponseEntity.ok(response);
	 }

	 
	 
	 @PostMapping("/user/add-address")
	    public ResponseEntity<?> AddSellerAddress(@RequestBody SellerAddressDTO seller){
	    	Response response=userService.saveUserAddress(seller);
	    			
	    	return ResponseEntity.ok(response);
	    }
	    
	    @PostMapping("/user/update-address")
	    public ResponseEntity<?> UpdateSellerAddress(@RequestBody UserAddressResponse seller){
	    	Response response=userService.updateAddress(seller);
			
	    	return ResponseEntity.ok(response);
	    }
	    
	    @DeleteMapping("/user/delete-address/{id}")
	    public ResponseEntity<?> DeleteAddress(@PathVariable String id){
	    	try {
	    		userService.deleteAddress(id);
	    		return ResponseEntity.ok("Address Deleted!!");
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return ResponseEntity.badRequest().body("Error while delete!!"+e.getMessage());

	    	}
	    }
	    
	    @GetMapping("/user/get-address")
	    public ResponseEntity<?> GetSellerAddress(){
	    	Response response=userService.getAllAddresses();
	    	return ResponseEntity.ok(response);
	    }
	    
	    @PostMapping("/user/mark-default-address")
	    public ResponseEntity<?> MakeAddressDefault(@RequestParam(name="id") String Id){
	    	Response response=userService.markAsDefault(Id);
	    	return ResponseEntity.ok(response);
	    	
	    }
}
	
