package com.techchallenge.user_manager_api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AtualizarSenhaRequestDTO(
        @Schema(description = "Senha atual da pessoa precisa estar preenchido")
        @NotBlank(message = "Senha atual da pessoa precisa estar preenchido")
        String senhaAtual,
        @Schema(description = "Nova senha da pessoa precisa estar preenchido")
        @NotBlank(message = "Nova senha da pessoa precisa estar preenchido")
        String novaSenha
) {
}
