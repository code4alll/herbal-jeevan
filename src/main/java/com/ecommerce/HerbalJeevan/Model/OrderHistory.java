package com.ecommerce.HerbalJeevan.Model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ecommerce.HerbalJeevan.Enums.OrderStage;
import com.ecommerce.HerbalJeevan.Enums.OrderStatus;

@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    @Enumerated(EnumType.STRING)
    private OrderStage stage;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String description;

    private Date lastUpdatedDate;
    
    


    private String orderedBy; // Can be userId or username

    // Getters, setters, and other methods

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

 

    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	

    public OrderStage getStage() {
		return stage;
	}

	public void setStage(OrderStage stage) {
		this.stage = stage;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }
    

	

	public OrderHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}

