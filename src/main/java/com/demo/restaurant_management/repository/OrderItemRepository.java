package com.demo.restaurant_management.repository;

import com.demo.restaurant_management.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder_Id(Integer orderId);
}
