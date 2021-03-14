package com.infy.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
