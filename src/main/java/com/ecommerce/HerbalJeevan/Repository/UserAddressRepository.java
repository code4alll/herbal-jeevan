package com.ecommerce.HerbalJeevan.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.HerbalJeevan.Model.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, String> {

	void deleteByIdAndUserId(String addressId, String sellerId);

	Optional<UserAddress> findByIdAndUserId(String addressId, String sellerId);
}
