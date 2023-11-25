package com.demo.restaurant_management.web.dto.request;

import lombok.Data;

@Data
public class CustomerRequest {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
}
