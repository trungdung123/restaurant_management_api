package com.demo.restaurant_management.service.impl;

import com.demo.restaurant_management.model.Customer;
import com.demo.restaurant_management.model.MenuItem;
import com.demo.restaurant_management.model.Order;
import com.demo.restaurant_management.model.OrderItem;
import com.demo.restaurant_management.model.constants.OrderStatus;
import com.demo.restaurant_management.repository.CustomerRepository;
import com.demo.restaurant_management.repository.MenuItemRepository;
import com.demo.restaurant_management.repository.OrderItemRepository;
import com.demo.restaurant_management.repository.OrderRepository;
import com.demo.restaurant_management.service.OrderService;
import com.demo.restaurant_management.service.utils.MappingHelper;
import com.demo.restaurant_management.web.dto.CategoryDto;
import com.demo.restaurant_management.web.dto.CustomerDto;
import com.demo.restaurant_management.web.dto.MenuItemDto;
import com.demo.restaurant_management.web.dto.OrderDto;
import com.demo.restaurant_management.web.dto.request.OrderRequest;
import com.demo.restaurant_management.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final MenuItemRepository menuItemRepository;
    private final MappingHelper mappingHelper;

    @Override
    public List<OrderDto> getAllOrder() {
        return orderRepository
                .findAll()
                .stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderId(Integer orderId) {
        return orderRepository
                .findById(orderId)
                .map(this::mapToOrderDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        Order.class.getName(),
                        orderId.toString()
                ));
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderRequest orderRequest) {
        var customer = customerRepository
                .findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        Customer.class.getName(),
                        orderRequest.getCustomerId().toString()
                ));

        var order = mappingHelper.map(orderRequest, Order.class);
        order.setCustomer(customer);

        var orderItems = menuItemRepository
                .findAllById(
                        orderRequest.getOrderItemRequests()
                                .stream()
                                .map(OrderRequest.OrderItemRequest::getMenuItemId)
                                .collect(Collectors.toList()))
                .stream()
                .map(menuItem -> {
                    var orderItemReq = orderRequest
                            .getOrderItemRequests()
                            .stream().filter(e -> e.getMenuItemId().equals(menuItem.getId()))
                            .findFirst();
                    if (orderItemReq.isPresent()) {
                        var orderItem = mappingHelper.map(orderItemReq.get(), OrderItem.class);
                        orderItem.setOrder(order);
                        orderItem.setMenuItem(menuItem);
                        return orderItem;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        var totalAmount = orderItems
                .stream()
                .flatMapToDouble(e -> DoubleStream.of(e.getMenuItem().getPrice() * e.getQuantity()))
                .sum();
        order.setTotalAmount((float) totalAmount);

        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(new Date());
        orderRepository.save(order);

        return mapToOrderDto(order);
    }

    @Override
    @Transactional
    public Integer deleteOrder(Integer orderId) {
        var order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        Order.class.getName(),
                        orderId.toString()
                ));
        orderRepository.delete(order);
        return orderId;
    }

    @Override
    public List<OrderDto> getOrdersOfCustomer(Integer customerId) {
        return orderRepository
                .findByCustomer_Id(customerId)
                .stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(Integer orderId, Integer orderStatus, String paymentMethod) {
        var order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        Order.class.getName(),
                        orderId.toString()
                ));
        order.setStatus(OrderStatus.values()[orderStatus]);
        order.setPaymentMethod(paymentMethod);
        orderRepository.save(order);
        return mapToOrderDto(order);
    }

    private OrderDto mapToOrderDto(Order order) {
        var orderDto = mappingHelper.map(order, OrderDto.class);
        orderDto.setCustomerDto(mappingHelper.map(order.getCustomer(), CustomerDto.class));
        orderDto.setOrderItemsDto(orderItemRepository
                .findByOrder_Id(order.getId())
                .stream()
                .map(this::mapToOrderItemDto)
                .collect(Collectors.toList()));
        return orderDto;
    }

    private OrderDto.OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        var orderItemDto = mappingHelper.map(orderItem, OrderDto.OrderItemDto.class);
        orderItemDto.setMenuItemDto(mapToMenuItemDto(orderItem.getMenuItem()));
        return orderItemDto;
    }

    private MenuItemDto mapToMenuItemDto(MenuItem menuItem) {
        var menuItemDto = mappingHelper.map(menuItem, MenuItemDto.class);
        menuItemDto.setCategoryDto(mappingHelper.map(menuItem.getCategory(), CategoryDto.class));
        return menuItemDto;
    }
}
