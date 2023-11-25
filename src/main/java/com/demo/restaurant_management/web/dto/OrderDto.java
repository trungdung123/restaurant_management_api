package com.demo.restaurant_management.web.dto;

import com.demo.restaurant_management.model.constants.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private Integer id;
    private CustomerDto customerDto;

    private List<OrderItemDto> orderItemsDto;

    private Date orderDate;
    private String paymentMethod;
    private Float totalAmount;
    private OrderStatus status;
    private String note;

    @Data
    public static class OrderItemDto {
        private Integer id;
        private MenuItemDto menuItemDto;
        private Integer quantity;
    }
}
