package com.ecommerce.HerbalJeevan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.HerbalJeevan.Model.OrderHistory;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long>{

}
