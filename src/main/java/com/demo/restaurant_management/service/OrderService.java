package com.demo.restaurant_management.service;

import com.demo.restaurant_management.model.constants.OrderStatus;
import com.demo.restaurant_management.web.dto.OrderDto;
import com.demo.restaurant_management.web.dto.request.OrderRequest;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrder();

    OrderDto getOrderId(Integer orderId);

    OrderDto createOrder(OrderRequest orderRequest);

    Integer deleteOrder(Integer orderId);

    List<OrderDto> getOrdersOfCustomer(Integer customerId);

    OrderDto updateOrderStatus(Integer orderId, Integer orderStatus, String paymentMethod);
}
