package com.ecommerce.HerbalJeevan.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.ecommerce.HerbalJeevan.Enums.Roles;


@Entity
public class User extends UserModel {
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = -7288468355848523701L;
	@OneToMany(mappedBy = "userId", fetch=FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAddress> address;
	  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
	  private Cart carts;
	 
	  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	  private List<Order> orders;
	  
	  
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	    private List<PaymentMethod> paymentMethods;


		public User() {
			super();
			// TODO Auto-generated constructor stub
		}





		public User(String username, String password, String email, String firstname, String lastname, String country) {
			super(username, password, email, firstname, lastname, country);
			// TODO Auto-generated constructor stub
		}





		public List<UserAddress> getAddress() {
			return address;
		}





		public void setAddress(List<UserAddress> address) {
			this.address = address;
		}





		public Cart getCarts() {
			return carts;
		}





		public void setCarts(Cart carts) {
			this.carts = carts;
		}





		public List<Order> getOrders() {
			return orders;
		}





		public void setOrders(List<Order> orders) {
			this.orders = orders;
		}





		public List<PaymentMethod> getPaymentMethods() {
			return paymentMethods;
		}





		public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
			this.paymentMethods = paymentMethods;
		}


		


		
	    
	    

}
