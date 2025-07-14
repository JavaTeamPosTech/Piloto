package com.techchallenge.user_manager_api.domain.dto.requests;

import com.techchallenge.user_manager_api.naousar.entities.enums.StatusContaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AtualizarProprietarioRequestDTO(
        @Schema(description = "Numero do CNPJ precisa estar preenchido", example = "12.345.678/0001-95")
        @NotBlank(message = "Numero do CNPJ precisa estar preenchido")
        String cnpj,

        @Schema(description = "Razão Social precisa estar preenchido", example = "Empresa de Exemplo LTDA")
        @NotBlank(message = "Razão Social precisa estar preenchido")
        String razaoSocial,

        @Schema(description = "Nome fantasia precisa estar preenchido", example = "Exemplo Comércio")
        @NotBlank(message = "Nome fantasia precisa estar preenchido")
        String nomeFantasia,

        @Schema(description = "Inscrição estadual precisa estar preenchido",  example = "1234567890")
        @NotBlank(message = "Inscrição estadual precisa estar preenchido")
        String inscricaoEstadual,

        @Schema(description = "Telefone comercial precisa estar preenchido",  example = "+5581999999999")
        @Pattern(regexp = "\\+?[0-9]{8,15}", message = "Numero de telefone inválido")
        @NotBlank(message = "Telefone comercial precisa estar preenchido")
        String telefoneComercial,

        @Schema(description = "Whatsapp precisa estar preenchido",  example = "+5581999999999")
        @Pattern(regexp = "\\+?[0-9]{8,15}", message = "Numero de telefone inválido")
        @NotBlank(message = "Whatsapp precisa estar preenchido")
        String whatsapp,

        @Schema(description = "Status da conta do proprietário", example = "ATIVO")
        StatusContaEnum statusConta,

        @Schema(description = "Nome do proprietário precisa estar preenchido",  example = "João Lima")
        @NotBlank(message = "Nome da pessoa precisa estar preenchido")
        String nome,

        @Schema(description = "Email do proprietário precisa estar preenchido", example ="joaolima@empresa.com")
        @NotBlank(message = "Email da pessoa precisa estar preenchido")
        String email,

        @Schema(description = "Login da pessoa precisa estar preenchido", example = "joaolima")
        @NotBlank(message = "Login da pessoa precisa estar preenchido")
        @Size(min = 4, max = 30, message = "O login deve ter entre 4 e 30 caracteres")
        String login,

        @Schema(description = "Lista de endereços do usuário (pelo menos 1)")
        @NotNull(message = "A lista de endereços não pode ser nula")
        @Size(min = 1, message = "Pelo menos um endereço deve ser informado")
        @Valid
        List<EnderecoRequestDTO> enderecos
) {
}
