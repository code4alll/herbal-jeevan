package com.ecommerce.HerbalJeevan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.HerbalJeevan.Model.Order;

public interface OrderRepository extends JpaRepository<Order, String>{

}
