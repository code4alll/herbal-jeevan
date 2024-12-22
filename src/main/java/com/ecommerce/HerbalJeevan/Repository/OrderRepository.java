package com.ecommerce.HerbalJeevan.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.HerbalJeevan.Enums.PaymentStatus;
import com.ecommerce.HerbalJeevan.Model.Order;
import com.ecommerce.HerbalJeevan.Model.TransactionDetailModel;
import com.ecommerce.HerbalJeevan.Model.User;
import com.ecommerce.HerbalJeevan.Model.UserAddress;

public interface OrderRepository extends JpaRepository<Order, String>{

	Order findByOrderId(String order);

	Order findOrderBytransactionDetail(TransactionDetailModel transactionDetail);

	List<Order> findOrdersByUser(User user);

	@Query("select o from Order o  join o.transactionDetail t where o.user=:user and t.paymentStatus not in (:status) ")
	Page<Order> findAllbyUser(@Param("user") User user, @Param("status") List<PaymentStatus> status, Pageable page);

	@Query("select o.id from Order o join o.transactionDetail t where o.user=:user and t.paymentStatus not in (:status) ")
	Set<String> findOrderIdByBuyer(@Param("user")User user,@Param("status")List<PaymentStatus> status);
	
	@Query("select o from Order o  join o.transactionDetail t where t.paymentStatus not in (:status) and o.id in (:orderId)")
	Page<Order> findOrdersByid(@Param("orderId") List<String> orderids, @Param("status") List<PaymentStatus> status, Pageable page);
    
	@Query("select o from Order o join o.transactionDetail t join o.user u where t.paymentStatus not in (:status)")
	List<Order> findAllOrders(@Param("status") List<PaymentStatus> status);

	void deleteBydeliveryAddress(UserAddress address);

}
