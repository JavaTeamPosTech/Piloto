package com.techchallenge.user_manager_api.domain.dto.response;

public record CadastroResponseDTO(
        UsuarioResponseDTO usuario,
        String token
) {
}
