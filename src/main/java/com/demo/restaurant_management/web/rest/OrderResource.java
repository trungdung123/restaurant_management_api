package com.demo.restaurant_management.web.rest;

import com.demo.restaurant_management.model.constants.OrderStatus;
import com.demo.restaurant_management.service.OrderService;
import com.demo.restaurant_management.web.dto.request.OrderRequest;
import com.demo.restaurant_management.web.dto.response.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderResource {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrder() {
        return ResponseUtils.ok(orderService.getAllOrder());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersOfCustomer(@PathVariable Integer customerId) {
        return ResponseUtils.ok(orderService.getOrdersOfCustomer(customerId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderId(@PathVariable Integer orderId) {
        return ResponseUtils.ok(orderService.getOrderId(orderId));
    }

    @PatchMapping("/{orderId}/update-status/{orderStatus}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Integer orderStatus,
            @PathVariable Integer orderId,
            @RequestPart(name = "paymentMethod") String paymentMethod) {
        return ResponseUtils.ok(orderService.updateOrderStatus(orderId, orderStatus, paymentMethod));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseUtils.ok(orderService.createOrder(orderRequest));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer orderId) {
        return ResponseUtils.ok("Removed", orderService.deleteOrder(orderId));
    }
}
