package com.demo.restaurant_management.service.impl;

import com.demo.restaurant_management.model.Account;
import com.demo.restaurant_management.model.Profile;
import com.demo.restaurant_management.repository.AccountRepository;
import com.demo.restaurant_management.repository.ProfileRepository;
import com.demo.restaurant_management.service.AuthService;
import com.demo.restaurant_management.service.utils.MappingHelper;
import com.demo.restaurant_management.web.dto.request.LoginRequest;
import com.demo.restaurant_management.web.dto.request.SignupRequest;
import com.demo.restaurant_management.web.dto.response.JwtResponse;
import com.demo.restaurant_management.web.exception.ServiceException;
import com.demo.restaurant_management.web.security.AuthoritiesConstants;
import com.demo.restaurant_management.web.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final MappingHelper mappingHelper;

    @Override
    public JwtResponse authenticateAccount(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtils.generateJwtToken(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> authorities = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return new JwtResponse(jwtToken, "Bearer", userDetails.getUsername(), authorities.get(0));

        } catch (AuthenticationException authenticationException) {
            throw new ServiceException("Username or password is invalid", "err.authorize.unauthorized");
        }
    }

    @Override
    public void registerAccount(SignupRequest signupRequest) {
        if (accountRepository.existsByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail()))
            throw new ServiceException("Email or username is existed in system", "err.api.email-username-is-existed");

        Account account = new Account();
        account.setUsername(signupRequest.getUsername());
        account.setEmail(signupRequest.getEmail());
        account.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        account.setRole(AuthoritiesConstants.CUSTOMER);
        accountRepository.save(account);

        var profile = mappingHelper.map(signupRequest.getProfileRequest(), Profile.class);
        profile.setAccount(account);
        profileRepository.save(profile);
    }
}
