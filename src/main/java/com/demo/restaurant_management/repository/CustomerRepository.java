package com.demo.restaurant_management.repository;

import com.demo.restaurant_management.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
