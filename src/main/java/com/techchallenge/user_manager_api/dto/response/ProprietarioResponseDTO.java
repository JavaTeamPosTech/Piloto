package com.techchallenge.user_manager_api.dto.response;

import com.techchallenge.user_manager_api.entities.enums.StatusContaEnum;

import java.util.List;
import java.util.UUID;

public record ProprietarioResponseDTO(
        UUID id,
        String cnpj,
        String razaoSocial,
        String nomeFantasia,
        String inscricaoEstadual,
        String telefoneComercial,
        String whatsapp,
        String nome,
        String email,
        String login,
        List<EnderecoResponseDTO> enderecos
) {
}
