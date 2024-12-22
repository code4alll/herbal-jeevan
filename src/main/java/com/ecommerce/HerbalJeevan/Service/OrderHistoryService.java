package com.ecommerce.HerbalJeevan.Service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.HerbalJeevan.Model.Order;
import com.ecommerce.HerbalJeevan.Model.OrderHistory;
import com.ecommerce.HerbalJeevan.Repository.OrderHistoryRepository;

@Service
public class OrderHistoryService {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    public OrderHistory saveOrderHistory(Order order, String description, String orderedBy) {
        OrderHistory orderHistory = new OrderHistory();
        

        orderHistory.setOrderId(order.getId());
        orderHistory.setStage(order.getStage()); // Store as String
        orderHistory.setStatus(order.getStatus()); // Store as String
        orderHistory.setDescription(description);
        orderHistory.setLastUpdatedDate(new Date());
        orderHistory.setOrderedBy(orderedBy);

        return orderHistoryRepository.save(orderHistory);
    }
}

