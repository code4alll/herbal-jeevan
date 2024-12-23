package com.ecommerce.HerbalJeevan.Controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.HerbalJeevan.DTO.AdminFilters;
import com.ecommerce.HerbalJeevan.DTO.LoginDto;
import com.ecommerce.HerbalJeevan.DTO.LoginResponse;
import com.ecommerce.HerbalJeevan.DTO.RegisterDto;
import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Enums.SortOption;
import com.ecommerce.HerbalJeevan.Service.OrderService;
import com.ecommerce.HerbalJeevan.Service.UserService;
import com.ecommerce.HerbalJeevan.Utility.Response;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/signup")
	private ResponseEntity<Response<?>> RegisterAdmin(@RequestBody RegisterDto user){
		return ResponseEntity.ok(userService.registerUser(user, Roles.ADMIN));
		
	}
	
	@PostMapping("/Login")
	private  ResponseEntity<?> LoginAdmin(@RequestBody LoginDto user){
		LoginResponse response=userService.LoginData(user,Roles.ADMIN);
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
	
	 @PostMapping("/forgot-password")
		private ResponseEntity<?> UpdatePassword(@RequestBody LoginDto updateDetails){
			Response<Object> res=new Response<>();
			if(updateDetails==null) {
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(false,"please enter required information"));
			}
			
			res=userService.ForgotPassword(updateDetails,Roles.ADMIN);
			return ResponseEntity.ok(res);
		}
	 
	 
	 @GetMapping("/get-user")
	    public ResponseEntity<?> getUser(
				@RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(required = false) String search,           
	            @RequestParam(defaultValue = "NEWEST") SortOption sort,
	            @RequestParam(required=false) Set<Long> id,
	            @RequestParam(required=false) Set<String> country,
	            @RequestParam(required=false) Set<String> name,
	            @RequestParam(required=true) Roles userType

	            
	            
	            ) {
	        Pageable pageable = PageRequest.of(page, size);
	        AdminFilters filter=new AdminFilters();
	        filter.setCountry(country);
	        filter.setId(id);
	        filter.setName(name);
			Response<?> res=userService.getAllUsers(pageable,filter,sort,userType);
			if(res==null||!res.getStatus()) {
				return ResponseEntity.badRequest().body(res);
			}
			return ResponseEntity.ok(res);
	        
	    }	
		
		@GetMapping("/get-user-details")
	    public ResponseEntity<?> getUserDetails(@RequestParam("id") String id) { 
			Response<?> res=userService.getUserById(id);
			if(res==null||!res.getStatus()) {
				return ResponseEntity.badRequest().body(res);
			}
			return ResponseEntity.ok(res);
	    }	
	 
	 @GetMapping("/get-all-orders")
	 private ResponseEntity<?> GetUserOrders(
			    @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size
	            ){
			Pageable pageable = PageRequest.of(page, size);
		 Response<?> res=orderService.getUserAdminOrders(pageable);
		 if(res.getStatus()) {
			 return ResponseEntity.ok(res);
		 }
		return ResponseEntity.badRequest().body(res);
	 }
	 
	 
	 @GetMapping("/get-order-details")
		public ResponseEntity<?> getOrderDetails(@RequestParam(required=true) String orderId){
			Response<?> res=orderService.getOrderDetails(orderId);
			return ResponseEntity.ok(res);
		}
	 @GetMapping("/get-overall-orders")
		public ResponseEntity<?> getOverall(
			    @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size
	            ){
			Pageable pageable = PageRequest.of(page, size);
			Response<?> res=orderService.overAllOrders(pageable);
			if(res.getStatus()) {
				 return ResponseEntity.ok(res);
			}
			return ResponseEntity.badRequest().body(res);
		}
	 
	 @GetMapping("/get-user-orders")
		public ResponseEntity<?> getUserOrders(
				@RequestParam(required=true) String userId,
				@RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(required = false) String search,           
	            @RequestParam(defaultValue = "NEWEST") SortOption sort){
	        Pageable pageable = PageRequest.of(page, size);
			Response<?> res=orderService.getAllUserOrder(pageable, search,sort,userId);
			if(res.getStatus()) {
				return ResponseEntity.ok(res);
			}
			return ResponseEntity.badRequest().body(res);
		}
	 
	 @DeleteMapping("/delete-user/{userId}")
		public ResponseEntity<?> deleteUser(@PathVariable String userId ){
			Response<?> res=userService.deleteUser(userId);
			return ResponseEntity.ok(res);
			
		}
		
	
}
