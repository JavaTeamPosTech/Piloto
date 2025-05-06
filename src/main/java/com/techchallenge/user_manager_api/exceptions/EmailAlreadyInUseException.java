package com.techchallenge.user_manager_api.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super("Já existe um cliente cadastrado com o email: " + message);
    }
}
