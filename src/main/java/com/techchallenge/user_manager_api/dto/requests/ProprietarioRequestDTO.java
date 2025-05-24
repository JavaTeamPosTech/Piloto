package com.techchallenge.user_manager_api.dto.requests;

import com.techchallenge.user_manager_api.entities.enums.StatusContaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ProprietarioRequestDTO(
        @Schema(description = "Numero do CNPJ precisa estar preenchido")
        @NotBlank(message = "Numero do CNPJ precisa estar preenchido")
        String cnpj,
        @Schema(description = "Razão Social precisa estar preenchido")
        @NotBlank(message = "Razão Social precisa estar preenchido")
        String razaoSocial,
        @Schema(description = "Nome fantasia precisa estar preenchido")
        @NotBlank(message = "Nome fantasia precisa estar preenchido")
        String nomeFantasia,
        @Schema(description = "Inscrição estadual precisa estar preenchido")
        @NotBlank(message = "Inscrição estadual precisa estar preenchido")
        String inscricaoEstadual,
        @Schema(description = "Telefone comercial precisa estar preenchido")
        @NotBlank(message = "Telefone comercial precisa estar preenchido")
        String telefoneComercial,
        @Schema(description = "Whatsapp precisa estar preenchido")
        @NotBlank(message = "Whatsapp precisa estar preenchido")
        String whatsapp,
        StatusContaEnum statusConta,
        @Schema(description = "Nome da pessoa precisa estar preenchido")
        @NotBlank(message = "Nome da pessoa precisa estar preenchido")
        String nome,
        String email,
        String login,
        String senha,
        List<EnderecoRequestDTO> enderecos
) {
}
