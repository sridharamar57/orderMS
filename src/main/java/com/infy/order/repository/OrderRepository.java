package com.infy.order.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.order.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Integer>{
	List<Order> findByBuyerId(Integer buyerId);
}
