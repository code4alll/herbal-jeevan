package com.ecommerce.HerbalJeevan.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.HerbalJeevan.Model.Cart;

public interface CartRepository extends JpaRepository<Cart, String>{

	@Query("select c from Cart c where c.user.userId=:userId")
	Optional<Cart> findByUserUserId(@Param("userId")String userId);

}
