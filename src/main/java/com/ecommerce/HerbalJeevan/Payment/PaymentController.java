package com.ecommerce.HerbalJeevan.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.HerbalJeevan.Enums.PaymentGatewayType;
import com.ecommerce.HerbalJeevan.Enums.PaymentStatus;
import com.ecommerce.HerbalJeevan.Service.CartService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment")
public class PaymentController { 
	@Autowired
    private PaymentService paymentService;
	
	@Autowired
	private CartService cartService;

	
//	@PostMapping("/test/get-transaction")
//	public ResponseEntity<?> createPayment(@RequestBody PaymentRequest request) {
//        try {
////            PaymentGatewayType gatewayType = PaymentGatewayType.valueOf(gateway.toUpperCase());
//            TransactionDetails transactionDetails = razorpay.createTransaction(request);
//            return ResponseEntity.ok(transactionDetails);
//        } catch (PaymentException | IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
	
//	@PostMapping("/test/callback")
//	public ResponseEntity<?> handleCallback(@RequestBody PaymentCallback callback) {
//		try {
////            PaymentGatewayType gatewayType = PaymentGatewayType.valueOf(gateway.toUpperCase());
//            PaymentStatus status = PaymentStatus.fromString(razorpay.handlePaymentCallback(callback).getMessage());
//            if (status == PaymentStatus.SUCCESS) {
//                return ResponseEntity.ok("Payment successful");
//            } else if (status == PaymentStatus.FAILED) {
//                return ResponseEntity.ok("Payment failed");
//            } else {
//                return ResponseEntity.ok(status);
//            }
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
	
	
	
	

    @PostMapping("/get-transaction")
    public ResponseEntity<?> createPayment(@RequestParam String orderId) {
        try {
            TransactionDetails transactionDetails = paymentService.createTransaction(PaymentGatewayType.RAZORPAY, orderId);
            return ResponseEntity.ok(transactionDetails);
        } catch (PaymentException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<String> handlePaymentCallback(@RequestBody PaymentCallback callback) {
        try {
//            PaymentStatus status = paymentService.handlePaymentCallback(callback, gatewayType);
            PaymentStatus status = PaymentStatus.fromString(paymentService.handlePaymentCallback(callback, PaymentGatewayType.RAZORPAY).getMessage());

            if (status == PaymentStatus.SUCCESS) {

                return ResponseEntity.ok("Payment successful");
            } else if (status == PaymentStatus.FAILED) {
                return ResponseEntity.ok("Payment failed");
            } else {
                return ResponseEntity.badRequest().body("Invalid payment status");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    
    
       
    }




