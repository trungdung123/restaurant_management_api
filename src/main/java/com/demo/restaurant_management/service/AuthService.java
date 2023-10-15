package com.demo.restaurant_management.service;

import com.demo.restaurant_management.web.dto.request.LoginRequest;
import com.demo.restaurant_management.web.dto.request.SignupRequest;
import com.demo.restaurant_management.web.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse authenticateAccount(LoginRequest loginRequest);

    void registerAccount(SignupRequest signupRequest);
}
