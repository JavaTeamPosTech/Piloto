package com.techchallenge.user_manager_api.domain.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClienteResponseDTO(
        UUID id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email,
        String login,
        String telefone,
        List<EnderecoResponseDTO> enderecos) {
}
