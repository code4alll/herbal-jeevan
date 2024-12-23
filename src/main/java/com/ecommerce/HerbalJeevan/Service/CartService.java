package com.ecommerce.HerbalJeevan.Service;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.HerbalJeevan.DTO.CartItemsDto;
import com.ecommerce.HerbalJeevan.DTO.CartResponseDto;
import com.ecommerce.HerbalJeevan.DTO.SingleImageResponse;
import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Enums.Status;
import com.ecommerce.HerbalJeevan.Model.Cart;
import com.ecommerce.HerbalJeevan.Model.CartItem;
import com.ecommerce.HerbalJeevan.Model.Product;
import com.ecommerce.HerbalJeevan.Model.User;
import com.ecommerce.HerbalJeevan.Repository.CartItemsRepository;
import com.ecommerce.HerbalJeevan.Repository.CartRepository;
import com.ecommerce.HerbalJeevan.Repository.ProductRepository;
import com.ecommerce.HerbalJeevan.Repository.UserRepo;
import com.ecommerce.HerbalJeevan.Utility.Response;



@Service
@SuppressWarnings({"rawtypes","unchecked","unused"})

public class CartService {
	@Autowired
   ProductRepository productRepo;
	@Autowired
   CartRepository cartRepo;
	@Autowired
   UserRepo userRepo;
	
       @Autowired
    UserService userService;
    

    
    @Autowired
    ProductService productService;
    @Autowired
    CartItemsRepository cartitemRepo;
	
	
	
	
	public CartResponseDto addToCart(String username, String productId, int quantity) {	
	        // Retrieve user's cart or create a new one if it doesn't exist
//		User user=(User) userRepo.findByUsernameAndRoleAndIsVerified(username,Roles.USER,Status.ACTIVE).orElse(null);
		Cart cart = cartRepo.findByUserUserId(UserService.getCurrentUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userService.getUserDetails());
                    return newCart;
                });
        

        Product product = productRepo.getProductByProductId(productId);

        // Check if product exists and is available
        if (product == null || !product.isAvailable()) {
            throw new RuntimeException("Product not found or not available: " + productId);
        }

        // Check if quantity is valid
        if (quantity <= 0) {
            throw new RuntimeException("Invalid quantity: " + quantity);
        }

        // Check if the product is already in the cart
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.updateCartTotal(cartItem.getSellPrice(),cartItem.getQuantity() );
        } else {
//        	public CartItem(Cart cart, String itemName, double itemPrice, int quantity, String gst, String productId) {

            // Add new cart item

    		SingleImageResponse singleimage=productService.GetSingleProductImage(product.getImages());
            cartItem = new CartItem(cart, product.getName(),Double.parseDouble(product.getOriginalPrice()),Double.parseDouble(product.getSalePrice()),quantity,product.getCategory().getGst(),product.getProductId());
            cartItem.setCart(cart);
            
            cartItem.setCurrency("INR");
            cartItem.setCurrencySymbol("");
            cartItem.setImageUrl(singleimage.getImageUrl());
            cartItem.setSize(singleimage.getImageSize());
            cartItem.setName(singleimage.getImageName());
            cartItem.setMinOrderQuant(0);
            cartItem.setUnitsPerCarton(0);
            // Set the cart for the cart item
            cartItem.updateCartTotal(cartItem.getSellPrice(),cartItem.getQuantity());
            cartItem.setSeller(product.getSeller());

            cart.getCartItems().add(cartItem);
            cart.setCurrency("INR");
            cart.setCurrencySymbol("INR");
            
            
        }
        
        

        // Update cart total
        cart.updateTotal();

        // Save cart
         cartRepo.saveAndFlush(cart);
//         user.setCarts(cart);
//         userRepo.saveAndFlush(user);
		return getCartData(username);
        
    }
	 public double calculateGstPrice(String itemPrice,Double gst) {
	        // Calculate GST amount
		 double basePriceDoublie = Double.parseDouble(itemPrice);
	     double gstRate = (basePriceDoublie*gst) / 100.0;
	        double totalPrice = basePriceDoublie + gstRate;

	        return totalPrice;
	    }
	 
	 public Double calculateGstPriceOnly(String price,Double gst) {
		    double gstAmount = (Double.parseDouble(price) * gst) / 100.0;
		    
		    DecimalFormat decimalFormat = new DecimalFormat("#.##");
		    return  Double.parseDouble(decimalFormat.format(gstAmount));

	 }
	public CartResponseDto getCartData(String email) {
		if(email!=null) {
			User user= userService.getUserDetails(email);
			
			Cart cart = user.getCarts()!=null?user.getCarts():null;
	        if(cart!=null) {
	        	try {
	        		List<CartItemsDto> cartitem=getCartItems(cart.getCartItems());  
	        		

	        		CartResponseDto response=new CartResponseDto(cartitem,cart.getCurrency(),cart.getCurrencySymbol(),cart.getTotalUnitPrice(),cart.getTotalSellPrice());
	        		response.setTotalUnitGstPrice(cart.getTotalUnitGstPrice());
	        		response.setTotalSellGstPrice(cart.getTotalSellGstPrice());
	        		return response;
	        	}catch(Exception e) {
	        		
	        		e.printStackTrace();
	        		
	        		return new CartResponseDto();
	        	}
	        	
	        }else {
	        	return new CartResponseDto();
	        }

			
		}
		return null;
		
		
	}
	
	
	@Transactional
	public void clearCart(User buyer) {
		
		
		
		if(buyer==null||buyer.getCarts()==null) {
			return;
		}
		try {
			
			Cart cart=buyer.getCarts();
				
			    cartRepo.delete(cart);
			    buyer.setCarts(null);
			    userRepo.saveAndFlush(buyer);
			    
				
		}catch(Exception e) {
			e.printStackTrace();

		}
		

		
	}
	
	public Response GetCartSummary(String email) {

		if(email!=null) {
			User user= userService.getUserDetails(email);
			
			Cart cart = user.getCarts()!=null?user.getCarts():null;
	        if(cart!=null) {
	        	try {
	        		

	        		CartResponseDto response=new CartResponseDto(cart.getCurrency(),cart.getCurrencySymbol(),cart.getTotalSellPrice());
	        		
	        		Map<String,Object> res=new HashMap<>();
	        		res.put("subtotal",response.getTotalSellPrice()+response.getTotalSellGstPrice());
	        		res.put("orderTotal",response.getTotalSellPrice()+response.getTotalSellGstPrice());
	        		res.put("currency",response.getCurrency());
	        		res.put("currencySymbol",response.getCurrencySymbol());
	        		return new Response(true,"Cart Summary fetched!!",res);
	        	}catch(Exception e) {
	        		
	        		e.printStackTrace();
	        		
	        		return new Response(false,"Error while fetching Cart Summary!!",e.getCause()+e.getMessage());
	        	}
	        	
	        }else {
	        	return new Response(false,"Error while fetching Cart Summary!!","cart not found!!");

	        }

			
		}
		return null;
		
		
	
	}
	
	
	public List<CartItemsDto> getCartItems(List<CartItem> cartItems) {
        List<CartItemsDto> response = new ArrayList<>();

        cartItems.forEach(item -> {
            try {
                CartItemsDto dtoItem = new CartItemsDto();
                SingleImageResponse image=new SingleImageResponse();
                dtoItem.setGst(item.getGst()+"%");
                dtoItem.setItemName(item.getItemName());
                dtoItem.setProductId(item.getProductId());
                dtoItem.setSellPrice(item.getSellPrice());
                dtoItem.setUnitPrice(item.getUnitPrice());
                dtoItem.setQuantity(item.getQuantity());
                dtoItem.setCurrency(item.getCurrency());
                dtoItem.setUnitsPerCarton(item.getUnitsPerCarton());
                dtoItem.setMinOrderQuant(item.getMinOrderQuant());
                dtoItem.setCurrencySymbol(item.getCurrencySymbol());
                dtoItem.setTotalAmount(item.getTotalAmount());
                dtoItem.setTotalTax(GetGstPrice(item.getSellPrice(),item.getGst(),item.getQuantity()));
                
                image.setImageUrl(item.getImageUrl());
                image.setImageSize(item.getSize());
                image.setImageName(item.getName());
                dtoItem.setImage(image);
                
                response.add(dtoItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return response;
    }
	
	public Double GetGstPrice(Double price,Double gst,Integer qty) {


	         double gstAmount = ((price * gst) / 100.0)*qty;

		 
		    DecimalFormat decimalFormat = new DecimalFormat("#.##");

		    
		    return Double.parseDouble(decimalFormat.format(gstAmount));
		    	
	}
   
	
	public CartResponseDto removeFromCart(String username, String productId) {
	    // Retrieve user's cart
		User user=(User) userRepo.findByUsernameAndRoleAndIsVerified(username,Roles.USER,Status.ACTIVE).orElse(null);
	    Cart cart = cartRepo.findByUserUserId(user.getUserId()).orElseThrow(() -> new RuntimeException("Cart not found for user: " + username));

	    // Find the cart item by productId
	    CartItem cartItem = cart.getCartItems().stream()
	            .filter(item -> item.getProductId().equals(productId))
	            .findFirst()
	            .orElse(null);

	    if (cartItem != null) {
	        // Remove the cart item from the cart
	        cart.getCartItems().remove(cartItem);
	        cartitemRepo.delete(cartItem);

	        // Update cart total
	        cart.updateTotal();
	        
	        // Save cart
	        cartRepo.saveAndFlush(cart);
	        // Update user's cart
	        user.setCarts(cart);
	        userRepo.saveAndFlush(user);
	        
			return getCartData(username);
 
	    } else {
	        throw new RuntimeException("Product not found in cart: " + productId);
	    }
	}

	public CartResponseDto adjustQuantity(String username, String productId, int newQuantity) {
	    // Retrieve user's cart
	    Cart cart = cartRepo.findByUserUserId(UserService.getCurrentUserId()).orElseThrow(() -> new RuntimeException("Cart not found for user: " + username));

	    // Find the cart item by productId
	    CartItem cartItem = cart.getCartItems().stream()
	            .filter(item -> item.getProductId().equals(productId))
	            .findFirst()
	            .orElse(null);

	    if (cartItem != null) {
	        if (newQuantity <= 0) {
	            // If new quantity is zero or negative, remove the item from the cart
	            cart.getCartItems().remove(cartItem);
		        cartitemRepo.delete(cartItem);
	        } else {
	            // Update the quantity of the cart item
	            cartItem.setQuantity(newQuantity);
	            cartItem.updateCartTotal(cartItem.getSellPrice(),cartItem.getQuantity());

	        }
	        // Update cart total
	        cart.updateTotal();
	        // Save cart
	        cartRepo.saveAndFlush(cart);
	        // Update user's cart
//	        user.setCarts(cart);
//	        userRepo.saveAndFlush(user);
			return getCartData(username);

	    } else {
	        throw new RuntimeException("Product not found in cart: " + productId);
	    }
	}

   
   

	
	
	
	

}
