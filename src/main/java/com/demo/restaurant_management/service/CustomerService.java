package com.demo.restaurant_management.service;

import com.demo.restaurant_management.web.dto.CustomerDto;
import com.demo.restaurant_management.web.dto.request.CustomerRequest;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomer();

    CustomerDto getCustomerById(Integer customerId);

    CustomerDto createCustomer(CustomerRequest customerRequest);

    CustomerDto updateCustomer(Integer customerId, CustomerRequest customerRequest);

    Integer deleteCustomer(Integer customerId);
}
