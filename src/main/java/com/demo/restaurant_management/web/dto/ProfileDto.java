package com.demo.restaurant_management.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileDto {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private Date dateOfBirth;
    private String gender;
    private String address;
    private AccountDto accountDto;
}
