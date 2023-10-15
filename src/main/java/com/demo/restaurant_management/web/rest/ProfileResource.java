package com.demo.restaurant_management.web.rest;

import com.demo.restaurant_management.service.ProfileService;
import com.demo.restaurant_management.web.dto.response.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me/profile")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ProfileResource {
    private final ProfileService profileService;

    @GetMapping("/customer-profile")
    public ResponseEntity<?> getCustomerProfile() {
        return ResponseUtils.ok(profileService.getCustomerProfile());
    }
}
