package com.demo.restaurant_management.web.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class ProfileRequest {
    private String fullName;
    @Pattern(regexp = "\"\\\\d{10}|(?:\\\\d{3}-){2}\\\\d{4}|\\\\(\\\\d{3}\\\\)\\\\d{3}-?\\\\d{4}\"")
    private String phoneNumber;
    private Date dateOfBirth;
    private String gender;
    private String address;
}
