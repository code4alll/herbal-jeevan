package com.ecommerce.HerbalJeevan.Controller;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ecommerce.HerbalJeevan.DTO.CartResponseDto;
import com.ecommerce.HerbalJeevan.Service.CartService;
import com.ecommerce.HerbalJeevan.Service.UserService;
import com.ecommerce.HerbalJeevan.Utility.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	CartService cartService;
	
    @Autowired
    JwtTokenUtil jwtTokenProvider;
    
    @Autowired
    UserService userService;
    
	
    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addProductToCart(
            @RequestParam("productId") String productId,
            @RequestParam("quantity") int qty) {

        String user = userService.GetUserEmail();

        if (StringUtils.isBlank(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not valid");
        }


        cartService.addToCart(user, productId, qty);
        return ResponseEntity.ok("Product " + productId + " added to cart");
    }
    
    
    @GetMapping("/get-cart")
    public ResponseEntity<?> getCartData() {
        String email = userService.GetUserEmail();

        if (StringUtils.isBlank(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not valid");
        }

     
        CartResponseDto response = cartService.getCartData(email);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/remove-item/{productId}")
    public ResponseEntity<?> GetUpdatedCart(@PathVariable(name="productId") String productId) {
        String email = userService.GetUserEmail();

        if (StringUtils.isBlank(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not valid");
        }

       

        CartResponseDto response = cartService.removeFromCart(email,productId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/adjust-item")
    public ResponseEntity<?> AdjustCart( @RequestParam("productId") String productId,
            @RequestParam("quantity") int qty) {
        String email = userService.GetUserEmail();

        if (StringUtils.isBlank(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not valid");
        }

       

        CartResponseDto response = cartService.adjustQuantity(email,productId,qty);
        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/cart-summary")
    public ResponseEntity<?> GetCartSummary(){

        String email = userService.GetUserEmail();

        if (StringUtils.isBlank(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not valid");
        }

        

        Response<?> response = cartService.GetCartSummary(email);
        return ResponseEntity.ok(response);
    
    	
    }
    
    
    


	 
}
			 
			 
		 
		 
			  



           
