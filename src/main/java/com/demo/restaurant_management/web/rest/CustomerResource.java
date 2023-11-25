package com.demo.restaurant_management.web.rest;

import com.demo.restaurant_management.service.CustomerService;
import com.demo.restaurant_management.web.dto.request.CustomerRequest;
import com.demo.restaurant_management.web.dto.response.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerResource {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomer() {
        return ResponseUtils.ok(customerService.getAllCustomer());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer customerId) {
        return ResponseUtils.ok(customerService.getCustomerById(customerId));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {
        return ResponseUtils.ok(customerService.createCustomer(customerRequest));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest customerRequest, @PathVariable Integer customerId) {
        return ResponseUtils.ok(customerService.updateCustomer(customerId, customerRequest));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseUtils.ok("Removed");
    }
}
