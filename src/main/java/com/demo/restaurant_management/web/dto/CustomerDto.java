package com.demo.restaurant_management.web.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
}
