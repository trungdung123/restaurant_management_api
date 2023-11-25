package com.demo.restaurant_management.web.dto.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequest {
    private Integer customerId;

    private List<OrderItemRequest> orderItemRequests;

    private Date orderDate;
    private String paymentMethod;
    private Float totalAmount;
    private String note;

    @Data
    public static class OrderItemRequest {
        private Integer menuItemId;
        private Integer quantity;
    }
}
