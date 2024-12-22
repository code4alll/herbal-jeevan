package com.ecommerce.HerbalJeevan.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.HerbalJeevan.Enums.PaymentStatus;
import com.ecommerce.HerbalJeevan.Model.Admin;
import com.ecommerce.HerbalJeevan.Model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	@Query("select o.order.id  from OrderItem o join TransactionDetailModel t on t.order.id=o.order.id where o.seller=:user and t.paymentStatus not in (:status)")
	Set<String> findAllOrderId(@Param("user")Admin user,@Param("status") List<PaymentStatus> status);

}
