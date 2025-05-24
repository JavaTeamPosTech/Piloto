package com.techchallenge.user_manager_api.exceptions.resolver;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConstraintViolationMessageResolver {

    private final Map<String, String> constraintMessages = Map.of(
            "uk_cliente_cpf", "Já existe um cadastro com este CPF.",
            "telefone", "Já existe um cadastro com este telefone.",
            "cnpj", "Já existe um cadastro com este CNPJ.",
            "uk_usuario_email", "Já existe um cadastro com este e-mail.",
            "usuarios_email_key", "Já existe um cadastro com este e-mail.",
            "uk_usuario_login", "Já existe um cadastro com este login."
    );

    public String resolveMessage(DataIntegrityViolationException ex) {
        String mensagemBanco = ex.getMostSpecificCause().getMessage();

        return constraintMessages.entrySet().stream()
                .filter(entry -> mensagemBanco.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("Já existe um registro com um campo único duplicado.");
    }
}
