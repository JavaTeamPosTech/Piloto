package com.techchallenge.user_manager_api.dto.response;

public record EnderecoResponseDTO(
        Long id,
        String estado,
        String cidade,
        String bairro,
        String rua,
        Integer numero,
        String complemento,
        String cep
) {
}
