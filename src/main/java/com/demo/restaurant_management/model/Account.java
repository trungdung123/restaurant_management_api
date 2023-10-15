package com.demo.restaurant_management.model;

import com.demo.restaurant_management.web.security.AuthoritiesConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @JsonIgnore
    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String password;
    @Email
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "role")
    private AuthoritiesConstants role;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    @JsonIgnore
    private Profile profile;
}
