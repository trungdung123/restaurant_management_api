package com.demo.restaurant_management.web.dto.request;

import com.demo.restaurant_management.web.security.AuthoritiesConstants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateAccountRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email(message = "Invalid email")
    private String email;

    private AuthoritiesConstants role;
    @NotBlank
    @Size(min = 5, max = 40)
    private String password;
    private ProfileRequest profileRequest;
}
