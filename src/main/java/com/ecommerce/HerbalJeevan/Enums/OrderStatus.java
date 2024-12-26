package com.ecommerce.HerbalJeevan.Enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum OrderStatus {
    PLACED("PLACED", Arrays.asList(OrderStage.CREATED, OrderStage.CONFIRM)),
    CONFIRMED("CONFIRMED", Arrays.asList(OrderStage.CONFIRM, OrderStage.PROCESSING)),
    SHIPPED("SHIPPED", Arrays.asList(OrderStage.READYTOSHIP, OrderStage.SHIPPED, OrderStage.ONTHEWAY)),
    DELIVERED("DELIVERED", Arrays.asList(OrderStage.OUTFORDELIVERY, OrderStage.DELIVERED)),
    CANCELLED("CANCELLED", Arrays.asList(OrderStage.CANCELLED)),
    RETURNED("RETURNED", Arrays.asList(OrderStage.RETURNEDCREATED, OrderStage.RETURNCONFIRMED, OrderStage.PICKUPINITIATED, OrderStage.PICKUPCOMPLETED)),
    REFUNDED("REFUNDED", Arrays.asList(OrderStage.REFUNDINITIATED, OrderStage.REFUNDED)),
    PENDING("PENDING", Arrays.asList(OrderStage.PAYMENT_PENDING, OrderStage.CREATED)),
    PROCESSING("PROCESSING", Arrays.asList(OrderStage.PROCESSING)),
    CREATED("CREATED", Arrays.asList(OrderStage.CREATED));

    private String status;
    private List<OrderStage> allowedStages;

    OrderStatus(String status, List<OrderStage> allowedStages) {
        this.status = status;
        this.allowedStages = allowedStages;
    }

    public static OrderStatus fromString(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getStatus().equalsIgnoreCase(status)) {
                return orderStatus;
            }
        }
        return null;
//        throw new IllegalArgumentException("No enum constant found for status: " + status);
    }

    public List<OrderStage> getAllowedStages() {
        return Collections.unmodifiableList(allowedStages);
    }

    @Override
    public String toString() {
        return status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public static String getValidStatus() {
    	StringBuilder builder=new StringBuilder();
    	builder.append("{ ");
    	for(OrderStatus stat:OrderStatus.values()) {
    		builder.append(stat.toString()).append(",");
    	}
    	builder.deleteCharAt(builder.lastIndexOf(","));
    	builder.append(" }");
    	
    	return builder.toString();
    }
}
