package com.ecommerce.HerbalJeevan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.HerbalJeevan.Model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
