package com.anz.orders.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Example: username: "admin", password: "password" (BCrypt encoded)
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("$2a$10$D4G5f18o7aMMfwasBL7Gpu8lHCgD3y8HZRHrPq3Fih8V2UFiLJr4K") // password = "password"
                    .authorities(Collections.emptyList())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
