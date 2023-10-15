package com.demo.restaurant_management.web.dto;

import com.demo.restaurant_management.web.security.AuthoritiesConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountDto {
    private Integer id;
    private String username;
    private String email;
    private AuthoritiesConstants role;
}
