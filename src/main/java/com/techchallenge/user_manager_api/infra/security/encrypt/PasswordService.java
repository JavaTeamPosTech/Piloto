package com.techchallenge.user_manager_api.infra.security.encrypt;

public interface PasswordService {
    String encryptPassword(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
