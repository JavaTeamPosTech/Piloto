package com.techchallenge.user_manager_api.dto.response;

public record CadastroResponseDTO(
        UsuarioResponseDTO usuario,
        String token
) {
}
