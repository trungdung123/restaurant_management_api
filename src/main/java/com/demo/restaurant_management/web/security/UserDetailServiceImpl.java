package com.demo.restaurant_management.web.security;

import com.demo.restaurant_management.model.Account;
import com.demo.restaurant_management.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (new EmailValidator().isValid(username, null)) {
            return accountRepository
                    .findOneByEmail(username)
                    .map(this::createUserSecurity)
                    .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' is not exist in system"));
        }

        return accountRepository
                .findOneByUsername(username)
                .map(this::createUserSecurity)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' is not exist in system"));

    }

    private org.springframework.security.core.userdetails.User createUserSecurity(Account account) {
        Set<GrantedAuthority> securityAuthorities = new HashSet<>(Collections.singleton(
                new SimpleGrantedAuthority(account.getRole().toString())));

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(), account.getPassword(), securityAuthorities);
    }

}
