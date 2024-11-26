package com.ecommerce.HerbalJeevan.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Model.UserModel;

public interface UserRepo extends JpaRepository<UserModel,Long> {

	Optional<UserModel> findByusername(String username);

	Optional<UserModel> findByUsernameAndRole(String userName, Roles role);

	boolean existsByEmailAndRole(String email, Roles fromString);

	boolean existsByUsernameAndRole(String username, Roles fromString);
	

}
