package com.ecommerce.HerbalJeevan.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.HerbalJeevan.Config.SecurityConfig.JwtTokenUtil;
import com.ecommerce.HerbalJeevan.Enums.PaymentGatewayType;
import com.ecommerce.HerbalJeevan.Service.OrderService;
import com.ecommerce.HerbalJeevan.Service.UserService;
import com.ecommerce.HerbalJeevan.Utility.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
//@ApiIgnore
public class OrderController {
	@Autowired
	OrderService orderService;
	  @Autowired
	    JwtTokenUtil jwtTokenProvider;
	  
	    @Autowired
	    UserService userService;

	    
	    
	@PostMapping("/place-order")
	private ResponseEntity<?> PlaceOrder(@RequestParam("address") String address
			){
		
		if(address==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("address id not present");
		}         
      Response<?> response=orderService.placeOrder(address,PaymentGatewayType.RAZORPAY);
      if(!response.getStatus()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

      }
      
		return ResponseEntity.status(HttpStatus.OK).body(response);

         
		
	}
	
	
	
	@GetMapping("/get-orders")
	private ResponseEntity<?> GetAllOrders(
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
		Pageable pageable = PageRequest.of(page, size);
		Response<?> response=orderService.getUserOrders(pageable);
	  if(!response.getStatus()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

      }
      
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	
	@PostMapping("/cancel-order/{orderId}")
	public ResponseEntity<Response<?>> cancelOrder(@PathVariable String orderId) {
	    Response<?> response = orderService.cancelOrder(orderId);
	    return ResponseEntity.ok(response);
	}
	
	
//	@GetMapping("/get-currency-rates")
//	public String getCurrencyRates() {
//		try {
//			currencyService.fetchAndSaveExchangeRates();
//			return "success";
//
//		}catch(Exception e) {
//			e.printStackTrace();
//			return e.getMessage();
//		}
//		
//	}
	
	@GetMapping("/get-invoice/{orderId}")
	public ResponseEntity<Response<?>> getInvoice(@PathVariable String orderId){
		if(orderId==null) {
			return ResponseEntity.badRequest().body(new Response<>(false,"orderId is null!!"));
		}
		Response<?> response=orderService.getOrderInvoice(orderId);
		if(!response.getStatus()) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
		
	}

	
	
}
