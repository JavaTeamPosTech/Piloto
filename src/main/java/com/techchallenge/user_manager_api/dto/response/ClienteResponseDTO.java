package com.techchallenge.user_manager_api.dto.response;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        Date dataNascimento,
        String email,
        String login,
        String telefone,
//        LocalDate ultimaAlteracao,
        List<EnderecoResponseDTO> enderecos) {
}
