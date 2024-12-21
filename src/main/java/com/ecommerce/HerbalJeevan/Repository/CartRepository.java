package com.ecommerce.HerbalJeevan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.HerbalJeevan.Model.Cart;

public interface CartRepository extends JpaRepository<Cart, String>{

}
