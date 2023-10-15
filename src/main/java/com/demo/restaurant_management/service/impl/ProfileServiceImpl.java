package com.demo.restaurant_management.service.impl;

import com.demo.restaurant_management.repository.ProfileRepository;
import com.demo.restaurant_management.service.ProfileService;
import com.demo.restaurant_management.service.utils.MappingHelper;
import com.demo.restaurant_management.web.dto.AccountDto;
import com.demo.restaurant_management.web.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final MappingHelper mappingHelper;

    @Override
    public ProfileDto getCustomerProfile() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return profileRepository.findByAccount_Username(username)
                .map(profile -> {
                    var profileDto = mappingHelper.map(profile, ProfileDto.class);
                    profileDto.setAccountDto(mappingHelper.map(profile.getAccount(), AccountDto.class));
                    return profileDto;
                })
                .orElseThrow(() -> new RuntimeException("Not found profile with account: " + username));
    }
}
