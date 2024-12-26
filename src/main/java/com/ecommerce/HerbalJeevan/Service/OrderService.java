package com.ecommerce.HerbalJeevan.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.HerbalJeevan.DTO.AdminOrderResponse;
import com.ecommerce.HerbalJeevan.DTO.AdminOrders;
import com.ecommerce.HerbalJeevan.DTO.Invoice;
import com.ecommerce.HerbalJeevan.DTO.OrderItemDto;
import com.ecommerce.HerbalJeevan.DTO.OrderResponseDTO;
import com.ecommerce.HerbalJeevan.DTO.PageResponse;
import com.ecommerce.HerbalJeevan.DTO.SellerDetailsResponse;
import com.ecommerce.HerbalJeevan.DTO.TotalOrderResponse;
import com.ecommerce.HerbalJeevan.DTO.UserAddressResponse;
import com.ecommerce.HerbalJeevan.Enums.OrderStage;
import com.ecommerce.HerbalJeevan.Enums.OrderStatus;
import com.ecommerce.HerbalJeevan.Enums.PaymentGatewayType;
import com.ecommerce.HerbalJeevan.Enums.PaymentStatus;
import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Enums.SortOption;
import com.ecommerce.HerbalJeevan.Model.Admin;
import com.ecommerce.HerbalJeevan.Model.Cart;
import com.ecommerce.HerbalJeevan.Model.CartItem;
import com.ecommerce.HerbalJeevan.Model.Order;
import com.ecommerce.HerbalJeevan.Model.OrderItem;
import com.ecommerce.HerbalJeevan.Model.TransactionDetailModel;
import com.ecommerce.HerbalJeevan.Model.User;
import com.ecommerce.HerbalJeevan.Model.UserAddress;
import com.ecommerce.HerbalJeevan.Model.UserModel;
import com.ecommerce.HerbalJeevan.Payment.PaymentCallback;
import com.ecommerce.HerbalJeevan.Payment.PaymentException;
import com.ecommerce.HerbalJeevan.Payment.PaymentService;
import com.ecommerce.HerbalJeevan.Payment.TransactionDetails;
import com.ecommerce.HerbalJeevan.Repository.OrderItemRepository;
import com.ecommerce.HerbalJeevan.Repository.OrderRepository;
import com.ecommerce.HerbalJeevan.Repository.TransactionDetailRepository;
import com.ecommerce.HerbalJeevan.Repository.UserAddressRepository;
import com.ecommerce.HerbalJeevan.Repository.UserRepo;
import com.ecommerce.HerbalJeevan.Utility.IdGeneratorUtils;
import com.ecommerce.HerbalJeevan.Utility.Response;



@Service
public class OrderService {
	
	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderHistoryService historyService;
    
    @Autowired
    private UserAddressRepository userAddressRepo;
    
    @Autowired
    UserRepo userRepo;
    
    @Autowired
	IdGeneratorUtils IdGenerator;
    
   
    @Autowired
    UserService userService;
    @Autowired
    TransactionDetailRepository transactionRepo;
    @Autowired
    PaymentService paymentService;
    

	 
	 @Autowired 
	OrderItemRepository orderItemsRepository;
	 
    private final List<PaymentStatus> status = Arrays.asList( PaymentStatus.UNDEFINED,PaymentStatus.CANCELLED);

   

    @Transactional
    public Response<?> placeOrder(String addressId,PaymentGatewayType gateway) {
        // Create a new Order
    	
    	
    	try {
    		
    		Order order;
    		
    	User user=userService.getUserDetails();
    	Cart cart=user.getCarts();
    	
    	if(cart==null) {
            return new Response<>(true,"order not created!!","No items in cart!!");
 
    	}
    	UserAddress deliveryAddress=userAddressRepo.findById(addressId).orElse(null);
    	
    	
            order = convertCartToOrder(cart);

    	
        if(order==null) {
            return new Response<>(true,"order not created!!","No items in cart!!");

        }
        order.setDeliveryAddress(deliveryAddress);
        order.setUser(user); 
        
        String description ="Order Awaiting for Confirmation";
        orderRepository.save(order);
        historyService.saveOrderHistory(order, description, user.getUsername());
        
        TransactionDetails transaction=paymentService.createTransaction(gateway, order.getOrderId());
        if(gateway.equals(PaymentGatewayType.PAYPAL)) {
        	transaction.setAmount((int) order.getTotalAmount());
        	transaction.setCurrency(order.getCurrency());
        }
        
        //it is enable while we not get razorpay
        transaction.setOrderId(order.getOrderId());
        return new Response<>(true,"ord	er created",transaction);

       
//        return new Response<>(true,"order created","Order Id: "+order.getOrderid());

    	}
    	catch(PaymentException e) {
    		return new Response<>(false,"order creation failled error in creating payment!!",e.getMessage()+" "+e.getCause());

    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return new Response<>(false,"order creation failled!!",e.getMessage());
    	}
        // Clear the cart
//        cartService.clearCart(userEmail);

//        return order;
    }
    
    
    private Order convertCartToOrder(Cart cart, String currency) {
		if(cart.getCartItems()==null) {
			return null;
		}
	    

	    // Create a new order
	    Order order = new Order();
	    order.setOrderDate(LocalDateTime.now());
	    order.generateOrderId();
	    order.setStatus(OrderStatus.PLACED);
	    order.setStage(OrderStage.CREATED);
	    order.setTotalSellGstPrice(cart.getTotalSellGstPrice());
	    order.setTotalUnitGstPrice(cart.getTotalUnitGstPrice());
	    order.setTotalSellPrice(cart.getTotalSellPrice());
	    order.setTotalUnitPrice(cart.getTotalUnitPrice());
	    order.setCurrency(cart.getCurrency());
	    order.setCurrencySymbol(cart.getCurrencySymbol());
	    

	    // Calculate total amount

	    // Create OrderDetails for each CartItem
	    for (CartItem cartItem : cart.getCartItems()) {
	        OrderItem orderDetail = new OrderItem();
	        orderDetail.setOrder(order);
	        orderDetail.setProductId(cartItem.getProductId());
	        orderDetail.setQuantity(cartItem.getQuantity());
	        orderDetail.setGst(cartItem.getGst());
	        orderDetail.setItemName(cartItem.getItemName());
	        orderDetail.setUnitPrice(cartItem.getUnitPrice());
	        orderDetail.setSellPrice(cartItem.getSellPrice());
	        orderDetail.setTotalAmount(cartItem.getTotalAmount());
	        orderDetail.setImageUrl(cartItem.getImageUrl());
	        orderDetail.setCurrency(cart.getCurrency());
	        orderDetail.setCurrencySymbol(cart.getCurrencySymbol());
	        orderDetail.setSize(cartItem.getSize());
	        orderDetail.setName(cartItem.getName());
	        orderDetail.setStatus(OrderStatus.PENDING);
	        orderDetail.setSeller(cartItem.getSeller());
	        
	        // Add to order items
	        order.getOrderItems().add(orderDetail);
	        
	        // Calculate total amount
	    }

	    // Set total amount and total GST
	    
	    order.updateTotal();
	    order.updateTotalGst();
	    
	    TransactionDetailModel transaction=new TransactionDetailModel();
        transaction.setOrder(order);
        transaction.setAmount(order.getTotalAmount());
        transaction.setCurrency(order.getCurrency());
        transaction.setPaymentStatus(PaymentStatus.PENDING);
        order.setTransactionDetail(transaction);

	    return order;
	}
    




    
    public OrderStatus getOrderStatus(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            return optionalOrder.get().getStatus();
        } else {
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }
    }

    public Response<?> updateOrderStatus(String orderId,OrderStatus status) {
    	try {
    	Order order=orderRepository.findByOrderId(orderId);
    	if(order==null) {
    		return new Response<>(false,"Order not found for orderId: "+orderId);
    	}
    	if(order!=null) {
    		order.getOrderItems().forEach(e->e.setStatus(status));
    	}
    	order.setStatus(status);
    	order.setStage(status.getAllowedStages().get(0));
    	if(status.equals(OrderStatus.DELIVERED)) {
    	order.getTransactionDetail().setPaymentStatus(PaymentStatus.SUCCESS);}
    	orderRepository.saveAndFlush(order);
    	return new Response<>(true,"Order Status updated Sucessfully!!","Order Status updated Sucessfully!!");
    	}catch(Exception e) {
    		e.printStackTrace();
    		return new Response<>(false,"Error while updating status",e.getCause()+" "+e.getMessage());
    	}
    	
    	
    }

	public void updateOrderStatus(PaymentCallback callback, PaymentStatus paymentStatus, String transactionId) {
		TransactionDetailModel transactionDetail=transactionRepo.findBypaymentId(callback.getOrderId());
		
		Order order=this.getOrderByTransactionId(transactionDetail);
		
		if(order!=null) {
			User buyer=order.getUser();

			if(PaymentStatus.SUCCESS.equals(paymentStatus)) {
				order.setStage(OrderStage.CONFIRM);
				order.setStatus(OrderStatus.PLACED);
				order.getOrderItems().forEach(or->{
					or.setStatus(OrderStatus.PLACED);
				});
				
				order.getTransactionDetail().setPaymentStatus(paymentStatus);
				order.getTransactionDetail().setTransactionId(transactionId);
				
				orderRepository.save(order);
                if(buyer!=null) {
					cartService.clearCart(buyer);
				}
			} else if(PaymentStatus.APPROVED.equals(paymentStatus)) {

				order.setStage(OrderStage.CONFIRM);
				order.setStatus(OrderStatus.PLACED);
				order.getOrderItems().forEach(or->{
					or.setStatus(OrderStatus.PLACED);
				});
				order.getTransactionDetail().setPaymentStatus(paymentStatus);
				orderRepository.save(order);
				if(buyer!=null) {
					cartService.clearCart(buyer);
				}
			
			}
			else if(PaymentStatus.PENDING.equals(paymentStatus)) {
				order.setStage(OrderStage.PAYMENT_PENDING);
				order.setStatus(OrderStatus.PENDING);
				order.getOrderItems().forEach(or->{
					or.setStatus(OrderStatus.PENDING);
				});
				order.getTransactionDetail().setPaymentStatus(paymentStatus);
				orderRepository.save(order);
			}else if(PaymentStatus.FAILED.equals(paymentStatus)) {
				order.setStage(OrderStage.PAYMENT_FAILED);
				order.setStatus(OrderStatus.CANCELLED);
				order.getOrderItems().forEach(or->{
					or.setStatus(OrderStatus.CANCELLED);
				});
				order.getTransactionDetail().setPaymentStatus(paymentStatus);
				orderRepository.save(order);
			}else if(PaymentStatus.CANCELLED.equals(paymentStatus)) {
				order.setStage(OrderStage.PAYMENT_FAILED);
				order.setStatus(OrderStatus.CANCELLED);
				order.getOrderItems().forEach(or->{
					or.setStatus(OrderStatus.CANCELLED);
				});
				order.getTransactionDetail().setPaymentStatus(paymentStatus);
				orderRepository.save(order);
			}
			else if(PaymentStatus.REFUNDED.equals(paymentStatus)) {
				order.setStage(OrderStage.REFUNDINITIATED);
				order.setStatus(OrderStatus.REFUNDED);
				order.getOrderItems().forEach(or->{
					or.setStatus(OrderStatus.REFUNDED);
				});
				order.getTransactionDetail().setPaymentStatus(paymentStatus);
				orderRepository.save(order);
			}
			
		}
		
	}


	

	public boolean verifyPaymentSignature(PaymentCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}


	public Order getOrderByOrderId(String order) {
		return orderRepository.findByOrderId(order) ;
	}
    
	
	public Order getOrderByTransactionId(TransactionDetailModel transactionDetail) {
		return orderRepository.findOrderBytransactionDetail(transactionDetail);
	}
	
	public Order updateOrder(Order order) {
		return orderRepository.save(order);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Response<?> getUserOrders(Pageable page) {
		try {
			
			List<PaymentStatus> status=new ArrayList<>();
//			status.add(PaymentStatus.PENDING);
//			status.add(PaymentStatus.CREATED);
			status.add(PaymentStatus.UNDEFINED);
			User user=userService.getUserDetails();
			Page<Order> orders=orderRepository.findAllbyUser(user,status,page);
			
			if(orders==null||orders.isEmpty()) {
				return new Response<>(false,"No order found!!");

			}
			
			List<OrderResponseDTO> response=new ArrayList<>();
			List<Order> or=orders.getContent();
			or.forEach(order->{
				if(order.getTransactionDetail().getPaymentStatus()!=null&&!(order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED))){
					OrderResponseDTO ord=getOrderResponse(order,null);
					response.add(ord);
				}

			});
			


			PageResponse respons=new PageResponse<>(orders,"orderDto");
			respons.setData(response);
			
			return new Response<>(true,response.size()+" orders found!!",respons);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new Response<>(false,"something went wrong while fetching order");
		}
		
		
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Response<?> getUserAdminOrders(Pageable page) {
		try {
			
			List<PaymentStatus> status=new ArrayList<>();
//			status.add(PaymentStatus.PENDING);
//			status.add(PaymentStatus.CREATED);
			status.add(PaymentStatus.UNDEFINED);
//			User user=userService.getUserDetails();
			Page<Order> orders=orderRepository.findAll(status,page);
			
			if(orders==null||orders.isEmpty()) {
				return new Response<>(false,"No order found!!");

			}
			
			List<AdminOrders> response=new ArrayList<>();
			List<Order> or=orders.getContent();
			or.forEach(order->{
				if(order.getTransactionDetail().getPaymentStatus()!=null&&!(order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED))){
					AdminOrders ord=getAdminOrder(order,null);
					response.add(ord);
				}

			});
			


			PageResponse respons=new PageResponse<>(orders,"orderDto");
			respons.setData(response);
			
			return new Response<>(true,response.size()+" orders found!!",respons);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new Response<>(false,"something went wrong while fetching order");
		}
		
		
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Response<?> getUserOrders(Pageable page,User user) {
		try {
			

//			Buyers user=userService.getBuyerDetails(userService.GetUserEmail());
			Page<Order> orders=orderRepository.findAllbyUser(user,status,page);
			
			if(orders==null||orders.isEmpty()) {
				return new Response<>(false,"No order found!!");

			}
			
			List<OrderResponseDTO> response=new ArrayList<>();
			List<Order> or=orders.getContent();
			or.forEach(order->{
				if(order.getTransactionDetail().getPaymentStatus()!=null&&!(order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED))){
					OrderResponseDTO ord=getOrderResponse(order,null);
					response.add(ord);
				}

			});
			


			PageResponse respons=new PageResponse<>(orders,"orderDto");
			respons.setData(response);
			
			return new Response<>(true,response.size()+" orders found!!",respons);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new Response<>(false,"something went wrong while fetching order");
		}
		
		
		
	}
	
	
	
//	public Response<?> getUserOrders(Pageable page,UserModel user) {
//		try {
//			
//			List<PaymentStatus> status=new ArrayList<>();
//			status.add(PaymentStatus.PENDING);
//			status.add(PaymentStatus.CREATED);
//			status.add(PaymentStatus.UNDEFINED);
//			Set<Long> orderids;
//			Page<Order> orders;
//			if(user.getRole().equals(Roles.SELLER)) {
//				orderids=orderItemsRepository.findAllOrderId((Sellers)user);
//	
//			}else if(user.getRole().equals(Roles.BUYER)) {
//				orderids=orderRepository.findOrderIdByBuyer((Buyers) user);
//			}
//			
//			if(orderids!=null&&orderids.size()>999) {
//				
//				orders=orderRepository.findOrdersByid(orderids,status,page);
//			}else {
//				orders=orderRepository.findOrdersByid(orderids,status,page);
//			}
//			if(orders==null||orders.isEmpty()) {
//				return new Response<>(false,"No order found!!");
//
//			}
//			
//			List<OrderResponse> response=new ArrayList<>();
//			List<Order> or=orders.getContent();
//			or.forEach(order->{
//				if(order.getTransactionDetail().getPaymentStatus()!=null&&!(order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.PENDING)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.CREATED)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED))){
//					OrderResponse ord=getOrderResponse(order);
//					response.add(ord);
//				}
//
//			});
//			
//
//
//			PageResponse respons=new PageResponse<>(orders,"orderDto");
//			respons.setData(response);
//			
//			return new Response<>(true,response.size()+" orders found!!",respons);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			return new Response<>(false,"something went wrong while fetching order");
//		}
//		
//		
//		
//	}
//	
	
	public Response<?> getUserOrders(Pageable page, UserModel user) {
	    try {
	        TreeSet<String> orderids;
	        Admin seller=null;

	        // Fetch order IDs based on the user's role and ensure uniqueness and sorting
	        if (user.getRole().equals(Roles.ADMIN)) {
	        	seller=(Admin) user;
	            orderids = new TreeSet<>(orderItemsRepository.findAllOrderId(seller,status));
	        } else if (user.getRole().equals(Roles.USER)) {
	            orderids = new TreeSet<>(orderRepository.findOrderIdByBuyer((User) user,status));
	        } else {
	            return new Response<>(false, "Invalid user role");
	        }

	        if (orderids == null || orderids.isEmpty()) {
	            return new Response<>(false, "No orders found!!");
	        }

	        // Fetch orders based on order IDs, status, and pagination, ensuring we only fetch needed elements
	        Page<Order> orders = fetchOrdersByPage(orderids, status, page);

	        if (orders == null || orders.isEmpty()) {
	            return new Response<>(false, "No orders found!!");
	        }

	        // Filter and prepare the response
	        List<OrderResponseDTO> response=new ArrayList<>();
			List<Order> or=orders.getContent();
			final Admin sellers=seller;
			or.forEach(order->{
				if(order.getTransactionDetail().getPaymentStatus()!=null&&!(order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.PENDING)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.CREATED)||order.getTransactionDetail().getPaymentStatus().equals(PaymentStatus.UNDEFINED))){
					OrderResponseDTO ord=getOrderResponse(order,sellers);
					response.add(ord);
				}

			});

	        // Return the paginated response
	        PageResponse<OrderResponseDTO> pageResponse = new PageResponse<>(new PageImpl<>(response, page, orders.getTotalElements()), "orderDto");
	        pageResponse.setData(response);
	        return new Response<>(true, response.size() + " orders found!!", pageResponse);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Response<>(false, "Something went wrong while fetching orders");
	    }
	}

	// Helper method to fetch orders in batches according to the page size and offset
	private Page<Order> fetchOrdersByPage(TreeSet<String> orderids, List<PaymentStatus> status, Pageable page) {
	    int total = orderids.size();
	    int start = (int) page.getOffset();
	    int end = Math.min(start + page.getPageSize(), total);

	    // Get the subset of sorted and unique order IDs for the current page
	    List<String> batchOrderIds = new ArrayList<>(orderids).subList(start, end);

	    // Fetch orders for the current page using the subset of order IDs
	    Page<Order> pagedOrders = orderRepository.findOrdersByid(batchOrderIds, status, page);
	    
	    return pagedOrders;
	}

	
	public Response<?> overAllOrders(Pageable page) {
//	    Map<Roles, Map<String, TotalOrderResponse>> res = new HashMap<>();
//	    Map<String, TotalOrderResponse> sellerMap = new HashMap<>();
//	    Map<String, TotalOrderResponse> buyerMap = new HashMap<>();

	    // Get all orders
	    Page<TotalOrderResponse> orders = orderRepository.findAllOrdersUser(status,page);
	    
	    // Return empty result if there are no orders
	    if (orders == null || orders.isEmpty()) {
	        return new Response<>(false,"no order found!!");
	    }

	    // Filter out valid orders based on payment status
//	    List<Order> validOrders = orders.stream()
//	        .filter(order -> {
//	            PaymentStatus status = order.getTransactionDetail().getPaymentStatus();
//	            return status != null && !(status.equals(PaymentStatus.PENDING) ||
//	                                       status.equals(PaymentStatus.CREATED) ||
//	                                       status.equals(PaymentStatus.UNDEFINED));
//	        })
//	        .collect(Collectors.toList());

	    // Process buyer orders
//	    orders.forEach(order -> {
//	        String buyerUsername = order.getUser().getUsername();
//	        TotalOrderResponse buyerResponse = buyerMap.getOrDefault(buyerUsername, new TotalOrderResponse(order.getUser()));
//	        buyerResponse.increateNoOfOrder(); // Increment order count for the buyer
//	        buyerMap.put(buyerUsername, buyerResponse); // Put updated response back into the map
//	    });

//	    // Process seller orders
//	    validOrders.forEach(order -> {
//	        order.getOrderItems().forEach(item -> {
//	            String sellerUsername = item.getSeller().getUsername();
//	            TotalOrderResponse sellerResponse = sellerMap.getOrDefault(sellerUsername, new TotalOrderResponse(item.getSeller()));
//	            sellerResponse.increateNoOfOrder(); // Increment order count for the seller
//	            sellerMap.put(sellerUsername, sellerResponse); // Put updated response back into the map
//	        });
//	    });

	    // Store results in the final response map
//	    res.put(Roles.USER, buyerMap);
//	    res.put(Roles.SELLER, sellerMap);

	    return new Response<>(true,"order found!!",orders);
	}

    
	
	public OrderResponseDTO getOrderResponse(Order order,Admin seller) {
		OrderResponseDTO response=new OrderResponseDTO();
		if(order==null) {
			return response;
		}
		List<OrderItemDto> orderitems=new ArrayList<>();
		if(order.getOrderItems()!=null) {
			order.getOrderItems().forEach(item->{
				if(seller==null) {
					OrderItemDto items=getOrderItemsDto(item);
					orderitems.add(items);

				}else if(seller.getUserId().equals(item.getSeller().getUserId())) {
					OrderItemDto items=getOrderItemsDto(item);
					orderitems.add(items);
				}
			});
		}
		
		response.setOrderId(order.getOrderId());
		response.setCurrency(order.getCurrency());
		response.setCurrencySymbol(order.getCurrencySymbol());
		response.setOrderItems(orderitems);
		response.setTotalPrice(String.valueOf(order.getTotalAmount()));
		response.setStage(order.getStage());
		response.setStatus(order.getStatus());
		response.setTotalSellPrice(order.getTotalSellPrice());
		response.setTotalUnitPrice(order.getTotalUnitPrice());
		response.setOrderDate(order.getOrderDate());
		return response;
		
		
		
	}
	
	public AdminOrders getAdminOrder(Order order,Admin seller) {
		AdminOrders response=new AdminOrders();
		if(order==null) {
			return response;
		}
		List<AdminOrderResponse> orderitems=new ArrayList<>();
		
		if(order.getOrderItems()!=null) {
			
			order.getOrderItems().forEach(item->{
					AdminOrderResponse items=getOrderItemsDtoForAdmin(item);
					if(items!=null&&items.getQuantity()!=0) {
						response.increaseQuantity(items.getQuantity());
					}
					orderitems.add(items);

				
			});
		}
		
		response.setOrderId(order.getOrderId());
		response.setCurrency(order.getCurrency());
		response.setCurrencySymbol(order.getCurrencySymbol());
		response.setOrderItems(orderitems);
		response.setTotalPrice(String.valueOf(order.getTotalAmount()));
		response.setStage(order.getStage());
		response.setStatus(order.getStatus());
		response.setTotalSellPrice(order.getTotalSellPrice());
		response.setTotalUnitPrice(order.getTotalUnitPrice());
		response.setOrderDate(order.getOrderDate());
		response.setBuyer(getBuyerDetail(order.getUser()));
		response.setAddress(UserAddressResponse.convertToResponseDTO(order.getDeliveryAddress()));
		return response;
		
		
		
	}
	
	public Map<String,Object> getBuyerDetail(User buyer){
		Map<String,Object> buyerDetail=new HashMap<>();
        if(buyer==null) {
			return new HashMap<>();
		  }
		
		try {
			buyerDetail.put("name", buyer.getFirstname()+" "+buyer.getLastname());
			buyerDetail.put("number", buyer.getMobile());
			buyerDetail.put("email", buyer.getEmail());
			buyerDetail.put("userId", buyer.getUserId());
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return buyerDetail;
	}
	
	
	
	
	public OrderItemDto getOrderItemsDto(OrderItem item) {
	  OrderItemDto response=new OrderItemDto();
		if(item==null) {
			return response;
		}
		response.setItemId(item.getId());
		response.setItemName(item.getItemName());
		response.setItemPrice(item.getSellPrice()+item.GetGstPrice(item.getSellPrice(), item.getGst(), 1));
		response.setQuantity(item.getQuantity());
		response.setProductId(item.getProductId());
		response.setSellPrice(item.getSellPrice());
		response.setImageUrl(item.getImageUrl());
		response.setSize(item.getSize());
		response.setGst(item.getGst()+"%");
		response.setName(item.getName());
		response.setCurrency(item.getCurrency());
		response.setCurrencySymbol(item.getCurrencySymbol());
		response.setTotalTax(item.GetGstPrice(item.getSellPrice(), item.getGst(), item.getQuantity()));
		response.setTotalAmount(item.getTotalAmount());
		
		SellerDetailsResponse s=new SellerDetailsResponse();
		if(item.getSeller()!=null) {
		s.setCompanyName("HERBAL JIVAN");
		s.setCountryOfoperation("INDIA");
		s.setIsVerified(item.getSeller().getIsVerified());
		s.setName(item.getSeller().getFirstname()+" "+item.getSeller().getLastname());
		response.setSeller(s);
	
		}else {
			response.setSeller(null);
		}
		
		
		
		
		return response;
		
		
	}
	
	public AdminOrderResponse getOrderItemsDtoForAdmin(OrderItem item) {
		AdminOrderResponse response=new AdminOrderResponse();
			if(item==null) {
				return response;
			}
			response.setItemId(item.getId());
			response.setItemName(item.getItemName());
			response.setItemPrice(item.getSellPrice()+item.GetGstPrice(item.getSellPrice(), item.getGst(), 1));
			response.setQuantity(item.getQuantity());
			response.setProductId(item.getProductId());
			response.setSellPrice(item.getSellPrice());
			response.setImageUrl(item.getImageUrl());
			response.setSize(item.getSize());
			response.setGst(item.getGst()+"%");
			response.setName(item.getName());
			response.setCurrency(item.getCurrency());
			response.setCurrencySymbol(item.getCurrencySymbol());
			response.setTotalTax(item.GetGstPrice(item.getSellPrice(), item.getGst(), item.getQuantity()));
			response.setTotalAmount(item.getTotalAmount());
			
			
			
			
			
			
			return response;
			
			
		}
	
	@SuppressWarnings("static-access")
	public Response<?> getOrderInvoice(String orderId){
		
		Order order=this.getOrderByOrderId(orderId);
		if(order==null) {
			return new Response<>(false,"No order Found for orderId "+orderId);
		}
		Invoice invoice=new Invoice();
		OrderResponseDTO orResponse=getOrderResponse(order,null);
		UserAddressResponse address=new UserAddressResponse();
		address=UserAddressResponse.convertToResponseDTO(order.getDeliveryAddress());
		invoice.setAddress(address);
		invoice.setOrderDetails(orResponse);
		invoice.setBuyer(order.getUser().getFirstname()+" "+order.getUser().getLastname());
		return new Response<>(true,"Invoice ready for orderId: "+orderId,invoice);
		
		
		
	}
    
	
	
	public Order convertCartToOrder(Cart cart) {
		if(cart.getCartItems()==null) {
			return null;
		}
	    // Create a new order
	    Order order = new Order();
	    order.setOrderDate(LocalDateTime.now());
	    order.generateOrderId();
	    order.setStatus(OrderStatus.PROCESSING);
	    order.setStage(OrderStage.CREATED);
	    order.setTotalSellGstPrice(cart.getTotalSellGstPrice());
	    order.setTotalUnitGstPrice(cart.getTotalUnitGstPrice());
	    order.setTotalSellPrice(cart.getTotalSellPrice());
	    order.setTotalUnitPrice(cart.getTotalUnitPrice());
	    order.setCurrency(cart.getCurrency());
	    order.setCurrencySymbol(cart.getCurrencySymbol());

	    // Calculate total amount

	    // Create OrderDetails for each CartItem
	    for (CartItem cartItem : cart.getCartItems()) {
	        OrderItem orderDetail = new OrderItem();
	        orderDetail.setOrder(order);
	        orderDetail.setProductId(cartItem.getProductId());
	        orderDetail.setQuantity(cartItem.getQuantity());
	        orderDetail.setGst(cartItem.getGst());
	        orderDetail.setItemName(cartItem.getItemName());
	        orderDetail.setUnitPrice(cartItem.getUnitPrice());
	        orderDetail.setSellPrice(cartItem.getSellPrice());
	        orderDetail.setTotalAmount(cartItem.getTotalAmount());
	        orderDetail.setImageUrl(cartItem.getImageUrl());
	        orderDetail.setCurrency(cartItem.getCurrency());
	        orderDetail.setCurrencySymbol(cartItem.getCurrencySymbol());
	        orderDetail.setSize(cartItem.getSize());
	        orderDetail.setName(cartItem.getName());
	        orderDetail.setStatus(OrderStatus.PENDING);
	        orderDetail.setSeller(cartItem.getSeller());
	        
	        // Add to order items
	        order.getOrderItems().add(orderDetail);
	        
	        // Calculate total amount
	    }

	    // Set total amount and total GST
	    
	    order.updateTotal();
	    order.updateTotalGst();
	    
	    TransactionDetailModel transaction=new TransactionDetailModel();
        transaction.setOrder(order);
        transaction.setAmount(order.getTotalAmount());
        transaction.setCurrency(order.getCurrency());
        transaction.setPaymentStatus(PaymentStatus.PENDING);
        order.setTransactionDetail(transaction);

	    return order;
	}




	public Response<?> cancelOrder(String orderId) {
		
		
		Order order=orderRepository.findByOrderId(orderId);
		if(order==null) {
			return new Response<>(false,"no order found with orderId: "+orderId);
		}
		order.setStage(OrderStage.CANCELLED);
		order.setStatus(OrderStatus.CANCELLED);
		order.getOrderItems().forEach(or->{
			or.setStatus(OrderStatus.CANCELLED);
		});
		orderRepository.save(order);
		Pageable pageable = PageRequest.of(0, 20);
		Response<?> response=this.getUserOrders(pageable);
		return new Response<>(true,"Order Cancelled sucessfully",response.getData());
	}


	public Response<?> getOrderDetails(String orderId) {
		Response<?> res=getOrderInvoice(orderId);
		if(res.getStatus())
		res.setMessage("Order fetched for orderId: "+orderId);
		return res;
	}


	public Response<?> getAllUserOrder(Pageable pageable, String search, SortOption sort, String userId) {
		User user=(User) userRepo.findById(userId).orElse(null);
		if(user==null) {
			return new Response<>(false,"User not found for userId: "+userId);
		}
		return getUserOrders(pageable, user);
	}

    
	

}
