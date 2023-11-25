package com.demo.restaurant_management.service.impl;

import com.demo.restaurant_management.model.Customer;
import com.demo.restaurant_management.repository.CustomerRepository;
import com.demo.restaurant_management.service.CustomerService;
import com.demo.restaurant_management.service.utils.MappingHelper;
import com.demo.restaurant_management.web.dto.CustomerDto;
import com.demo.restaurant_management.web.dto.request.CustomerRequest;
import com.demo.restaurant_management.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final MappingHelper mappingHelper;

    @Override
    public List<CustomerDto> getAllCustomer() {
        return customerRepository
                .findAll()
                .stream()
                .map(this::mapToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Integer customerId) {
        return customerRepository
                .findById(customerId)
                .map(this::mapToCustomerDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        Customer.class.getName(),
                        customerId.toString()
                ));
    }

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerRequest customerRequest) {
        var customer = mappingHelper.map(customerRequest, Customer.class);
        return mapToCustomerDto(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Integer customerId, CustomerRequest customerRequest) {
        var customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        Customer.class.getName(),
                        customerId.toString()
                ));
        mappingHelper.mapIfSourceNotNullAndStringNotBlank(customerRequest, customer);
        return mapToCustomerDto(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public Integer deleteCustomer(Integer customerId) {
        var customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        Customer.class.getName(),
                        customerId.toString()
                ));
        customerRepository.delete(customer);
        return customerId;
    }

    private CustomerDto mapToCustomerDto(Customer customer) {
        return mappingHelper.map(customer, CustomerDto.class);
    }
}
