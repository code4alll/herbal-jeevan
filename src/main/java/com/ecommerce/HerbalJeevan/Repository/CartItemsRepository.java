package com.ecommerce.HerbalJeevan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.HerbalJeevan.Model.CartItem;

public interface CartItemsRepository extends JpaRepository<CartItem, Long>{

}
