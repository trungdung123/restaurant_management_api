package com.demo.restaurant_management.service.impl;

import com.demo.restaurant_management.model.Account;
import com.demo.restaurant_management.model.Profile;
import com.demo.restaurant_management.repository.AccountRepository;
import com.demo.restaurant_management.repository.ProfileRepository;
import com.demo.restaurant_management.service.AccountService;
import com.demo.restaurant_management.service.utils.MappingHelper;
import com.demo.restaurant_management.web.dto.AccountDto;
import com.demo.restaurant_management.web.dto.ProfileDto;
import com.demo.restaurant_management.web.dto.request.CreateAccountRequest;
import com.demo.restaurant_management.web.exception.ServiceException;
import com.demo.restaurant_management.web.security.AuthoritiesConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;
    private final MappingHelper mappingHelper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ProfileDto> getAllAccountProfiles() {
        return profileRepository.findAll()
                .stream()
                .filter(e -> e.getAccount().getRole() != AuthoritiesConstants.ADMIN)
                .map(profile -> {
                    var profileDto = mappingHelper.map(profile, ProfileDto.class);
                    profileDto.setAccountDto(mappingHelper.map(profile.getAccount(), AccountDto.class));
                    return profileDto;
                }).collect(Collectors.toList());
    }

    @Override
    public ProfileDto createAccount(CreateAccountRequest createAccountRequest) {
        if (accountRepository.existsByUsernameOrEmail(createAccountRequest.getUsername(), createAccountRequest.getEmail()))
            throw new ServiceException("Email or username is existed in system", "err.api.email-username-is-existed");

        Account account = new Account();
        account.setUsername(createAccountRequest.getUsername());
        account.setEmail(createAccountRequest.getEmail());
        account.setPassword(passwordEncoder.encode(createAccountRequest.getPassword()));

        account.setRole(createAccountRequest.getRole());
        accountRepository.save(account);

        var profile = mappingHelper.map(createAccountRequest.getProfileRequest(), Profile.class);
        profile.setAccount(account);
        profileRepository.save(profile);

        var profileDto = mappingHelper.map(profile, ProfileDto.class);
        profileDto.setAccountDto(mappingHelper.map(account, AccountDto.class));
        return profileDto;
    }
}
