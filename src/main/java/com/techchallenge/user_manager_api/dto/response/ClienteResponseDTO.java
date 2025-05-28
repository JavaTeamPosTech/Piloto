package com.techchallenge.user_manager_api.dto.response;

import java.util.List;
import java.util.UUID;

public record ClienteResponseDTO(
        UUID id,
        String nome,
        String email,
        String login,
        List<EnderecoResponseDTO> enderecos) {
}
