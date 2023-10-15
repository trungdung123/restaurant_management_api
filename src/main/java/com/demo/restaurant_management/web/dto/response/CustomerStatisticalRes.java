package com.demo.restaurant_management.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatisticalRes {
    private Integer accountId;
    private String username;
    private String phoneNumber;
    private String fullName;
    private int value;
}
