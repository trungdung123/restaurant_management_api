package com.demo.restaurant_management.web.rest;

import com.demo.restaurant_management.service.AuthService;
import com.demo.restaurant_management.web.dto.request.LoginRequest;
import com.demo.restaurant_management.web.dto.request.SignupRequest;
import com.demo.restaurant_management.web.dto.response.utils.Response;
import com.demo.restaurant_management.web.dto.response.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthResource {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<Response> authenticateAccount(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseUtils.ok(authService.authenticateAccount(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> registerAccount(@Valid @RequestBody SignupRequest signupRequest) {
        authService.registerAccount(signupRequest);
        return ResponseUtils.created();
    }
}
