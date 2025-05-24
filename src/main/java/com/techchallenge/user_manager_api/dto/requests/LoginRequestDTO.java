package com.techchallenge.user_manager_api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @Schema(description = "Login da pessoa precisa estar preenchido")
        @NotBlank(message = "Login da pessoa precisa estar preenchido")
        String login,
        @Schema(description = "Senha da pessoa precisa estar preenchido")
        @NotBlank(message = "Senha da pessoa precisa estar preenchido")
        String senha
) {
}
