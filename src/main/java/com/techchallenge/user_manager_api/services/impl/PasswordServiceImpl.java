package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.services.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    public PasswordServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
