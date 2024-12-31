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
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.ecommerce.HerbalJeevan.Enums.ReviewStatus;
import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Enums.SortOption;
import com.ecommerce.HerbalJeevan.Service.OrderService;
import com.ecommerce.HerbalJeevan.Service.ProductServiceImp;
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
	@Autowired
	private ProductServiceImp productService;
	
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
	            @RequestParam(required=true) Roles userType) {
		 
	        Pageable pageable = PageRequest.of(page, size);
	        AdminFilters filter=new AdminFilters();
	        filter.setCountry(country);
	        filter.setId(id);
	        filter.setName(name);
			return getResponse(userService.getAllUsers(pageable,filter,sort,userType));
			
	        
	    }	
		
		@GetMapping("/get-user-details")
	    public ResponseEntity<?> getUserDetails(@RequestParam("id") String id) { 
			return getResponse(userService.getUserById(id));
			
	    }	
	 
	 @GetMapping("/get-all-orders")
	 private ResponseEntity<?> GetUserOrders(
			    @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size
	            ){
			Pageable pageable = PageRequest.of(page, size);
		 return getResponse(orderService.getUserAdminOrders(pageable));
		
	 }
	 
	 
	 @GetMapping("/get-order-details")
		public ResponseEntity<?> getOrderDetails(@RequestParam(required=true) String orderId){
			return getResponse(orderService.getOrderDetails(orderId));
		
		}
	 @GetMapping("/get-overall-orders")
		public ResponseEntity<?> getOverall(
			    @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size
	            ){
			Pageable pageable = PageRequest.of(page, size);
			return getResponse(orderService.overAllOrders(pageable));
		}
	 
	 @GetMapping("/get-user-orders")
		public ResponseEntity<?> getUserOrders(
				@RequestParam(required=true) String userId,
				@RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(required = false) String search,           
	            @RequestParam(defaultValue = "NEWEST") SortOption sort){
	        Pageable pageable = PageRequest.of(page, size);
			return getResponse(orderService.getAllUserOrder(pageable, search,sort,userId));
		}
	 
	 @DeleteMapping("/delete-user/{userId}")
		public ResponseEntity<?> deleteUser(@PathVariable String userId ){
			return getResponse(userService.deleteUser(userId));
			
		}
	 
	 @PatchMapping("/update-review-status")
	 public ResponseEntity<?> updateReviewStatus(@RequestParam("reviewId") Long reviewId,@RequestParam("status") ReviewStatus status){
		return getResponse(productService.updateReviewStatus(reviewId,status));
	 }
	 
	 @PatchMapping("/reply-question")
	 public ResponseEntity<?> ReplyQuestion(@RequestParam("id") Long questionId,@RequestParam("message") String message){
		return getResponse(productService.replyQuestion(questionId,message));		 
	 }
	 
	 @DeleteMapping("/delete-review/{reviewId}")
		public ResponseEntity<?> deleteUser(@PathVariable Long reviewId  ){
			return getResponse(productService.deleteReview(reviewId));
			
		}
	 
	 @DeleteMapping("/delete-question/{id}")
		public ResponseEntity<?> deleteQuestion(@PathVariable Long id ){
		 return getResponse(productService.deleteQuestion(id));
			
		}
	 
	 @GetMapping("/get-reviews")
	 public ResponseEntity<?> getReviews(
			    @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam String status){
		 Pageable pageable = PageRequest.of(page, size);
		 return getResponse(productService.getAdminRviews(status,pageable));
		 
	 }
	 @GetMapping("/get-questions")
	 public ResponseEntity<?> getQuestions( 
			    @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam String status){
		 Pageable pageable = PageRequest.of(page, size);
		 return getResponse(productService.getAdminQuestions(status,pageable));
		 
	 }
	 
	 
	 public ResponseEntity<?> getResponse(Response<?> res){
		 if(res!=null&&res.getStatus()) {
				return ResponseEntity.ok(res);
			}
			return ResponseEntity.badRequest().body(res);
	 }
	 
		
	
}
