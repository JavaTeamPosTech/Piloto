package com.techchallenge.user_manager_api.dto.response;

import com.techchallenge.user_manager_api.entities.enums.StatusContaEnum;

import java.time.LocalDate;
import java.util.List;

public record ProprietarioResponseDTO(
        Long id,
        String cnpj,
        String razaoSocial,
        String nomeFantasia,
        String inscricaoEstadual,
        String telefoneComercial,
        String whatsapp,
        StatusContaEnum statusConta,
        String nome,
        String email,
        String login,
        List<EnderecoResponseDTO> enderecos
) {
}
