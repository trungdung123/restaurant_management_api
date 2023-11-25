package com.demo.restaurant_management.repository;

import com.demo.restaurant_management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomer_Id(Integer customerId);
}
