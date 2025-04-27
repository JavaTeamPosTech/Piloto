package com.techchallenge.user_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioRequestDTO(
        @Schema(description = "Nome da pessoa precisa estar preenchido")
        @NotBlank(message = "Nome da pessoa precisa estar preenchido")
        String nome,
        String email,
        String login,
        String senha,
        List<EnderecoRequestDTO> enderecos
) {
}
