package com.techchallenge.user_manager_api.domain.dto.response;

import java.util.UUID;

public record EnderecoResponseDTO(
        UUID id,
        String estado,
        String cidade,
        String bairro,
        String rua,
        Integer numero,
        String complemento,
        String cep
) {
}
