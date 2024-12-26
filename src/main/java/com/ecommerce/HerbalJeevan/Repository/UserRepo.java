package com.ecommerce.HerbalJeevan.Repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.HerbalJeevan.DTO.UserDetailResponse;
import com.ecommerce.HerbalJeevan.Enums.Roles;
import com.ecommerce.HerbalJeevan.Enums.Status;
import com.ecommerce.HerbalJeevan.Model.User;
import com.ecommerce.HerbalJeevan.Model.UserModel;

public interface UserRepo extends JpaRepository<UserModel,String> {

	Optional<UserModel> findByusername(String username);

	Optional<UserModel> findByUsernameAndRoleAndIsVerified(String userName, Roles role,Status isVerified);
	User findByUserIdAndRoleAndIsVerified(String userId, Roles role,Status isVerified);


	@Query("SELECT COUNT(u) > 0 FROM UserModel u WHERE u.email = :email AND u.role = :role AND u.isVerified = :isVerified")
	boolean existsByEmailRoleAndIsVerified(@Param("email") String email, @Param("role") Roles role, @Param("isVerified") Status isVerified);

	@Query("SELECT COUNT(u) > 0 FROM UserModel u WHERE u.username = :username AND u.role = :role AND u.isVerified = :isVerified")
	boolean existsByUsernameRoleAndIsVerified(@Param("username") String username, @Param("role") Roles role, @Param("isVerified") Status isVerified);

	@Query("SELECT new com.ecommerce.HerbalJeevan.DTO.UserDetailResponse(u.id, u.firstname, u.lastname,u.email) " +
		       "FROM UserModel u " +
		       "WHERE u.isVerified = 'ACTIVE' " +
		       "AND (u.role=:userType) " +
		       "AND (:id IS NULL OR u.id IN :id) " +
		       "AND (:country IS NULL OR (u.country) IN (:country)) " +
		       "AND (:name IS NULL OR (u.firstname) IN (:name) OR (u.lastname) IN (:name))")
		Page<UserDetailResponse> findallUsers(@Param("country") Set<String> country, 
		                             @Param("id") Set<Long> id, 
		                             @Param("name") Set<String> name, 
		                             Pageable page, @Param("userType")Roles userType);	

}
