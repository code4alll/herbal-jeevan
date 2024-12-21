package com.ecommerce.HerbalJeevan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.HerbalJeevan.Model.TransactionDetailModel;


@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetailModel, String> {

	TransactionDetailModel findBypaymentId(String orderId);
}

