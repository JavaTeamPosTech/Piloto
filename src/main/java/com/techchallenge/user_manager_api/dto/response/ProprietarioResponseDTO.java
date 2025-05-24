package com.techchallenge.user_manager_api.dto.response;

import java.time.LocalDate;
import java.util.List;

public record ProprietarioResponseDTO (
        Long id,
        String nome,
        String email,
        String login,
        LocalDate ultimaAlteracao,
        List<EnderecoResponseDTO> enderecos
) {
}
