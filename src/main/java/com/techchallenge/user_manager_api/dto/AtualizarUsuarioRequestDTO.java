package com.techchallenge.user_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AtualizarUsuarioRequestDTO(
        @Schema(description = "Nome da pessoa precisa estar preenchido")
        @NotBlank(message = "Nome da pessoa precisa estar preenchido")
        String nome,
        @Schema(description = "Email da pessoa precisa estar preenchido")
        @NotBlank(message = "Email da pessoa precisa estar preenchido")
        String email,
        @Schema(description = "Endereco da pessoa precisa estar preenchido")
        @NotBlank(message = "Endereco da pessoa precisa estar preenchido")
        String endereco
) {
}
