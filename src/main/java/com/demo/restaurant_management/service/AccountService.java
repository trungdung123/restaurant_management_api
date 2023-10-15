package com.demo.restaurant_management.service;

import com.demo.restaurant_management.web.dto.ProfileDto;
import com.demo.restaurant_management.web.dto.request.CreateAccountRequest;

import java.util.List;

public interface AccountService {
    List<ProfileDto> getAllAccountProfiles();

    ProfileDto createAccount(CreateAccountRequest createAccountRequest);
}
