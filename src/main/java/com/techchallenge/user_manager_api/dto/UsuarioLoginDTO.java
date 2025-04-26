package com.techchallenge.user_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDTO(
        @Schema(description = "Email da pessoa precisa estar preenchido")
        @NotBlank(message = "Email da pessoa precisa estar preenchido")
        String email,
        @Schema(description = "Senha da pessoa precisa estar preenchido")
        @NotBlank(message = "Senha da pessoa precisa estar preenchido")
        String senha) {
}
