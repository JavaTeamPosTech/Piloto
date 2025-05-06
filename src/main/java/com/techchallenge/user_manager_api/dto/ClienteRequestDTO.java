package com.techchallenge.user_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ClienteRequestDTO(
        @Schema(description = "Nome da pessoa precisa estar preenchido")
        @NotBlank(message = "Nome da pessoa precisa estar preenchido")
        String nome,
        @Schema(description = "Email da pessoa precisa estar preenchido")
        @NotBlank(message = "Email da pessoa precisa estar preenchido")
        String email,
        @Schema(description = "Login da pessoa precisa estar preenchido")
        @NotBlank(message = "Login da pessoa precisa estar preenchido")
        String login,
        @Schema(description = "Senha da pessoa precisa estar preenchido")
        @NotBlank(message = "Senha da pessoa precisa estar preenchido")
        String senha,
        @Valid
        List<EnderecoRequestDTO> enderecos
) {
}
