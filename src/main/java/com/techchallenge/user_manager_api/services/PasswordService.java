package com.techchallenge.user_manager_api.services;

public interface PasswordService {
    String encryptPassword(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
