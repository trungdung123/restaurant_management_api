package com.demo.restaurant_management.web.rest.admin;

import com.demo.restaurant_management.service.AccountService;
import com.demo.restaurant_management.web.dto.request.CreateAccountRequest;
import com.demo.restaurant_management.web.dto.response.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/accounts")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AccountResource {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getAllAccount() {
        return ResponseUtils.ok(accountService.getAllAccountProfiles());
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return ResponseUtils.ok(accountService.createAccount(createAccountRequest));
    }
}
