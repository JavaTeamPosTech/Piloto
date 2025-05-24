package com.techchallenge.user_manager_api.dto.requests;

import com.techchallenge.user_manager_api.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.entities.enums.TiposComidaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;
import java.util.Set;

public record ClienteRequestDTO(

        @Schema(description = "CPF da pessoa precisa estar preenchido")
        @NotBlank(message = "CPF da pessoa precisa estar preenchido")
        String cpf,
        @Schema(description = "Data de nascimento da pessoa precisa estar preenchido")
        Date dataNascimento,
        @Schema(description = "Gênero da pessoa precisa estar preenchido")
        GeneroEnum genero,
        @Schema(description = "Telefone da pessoa precisa estar preenchido")
        @NotBlank(message = "Telefone da pessoa precisa estar preenchido")
        String telefone,
        @Schema(description = "Preferencias Alimentares da pessoa precisa estar preenchido")
        Set<TiposComidaEnum> preferenciasAlimentares,
        @Schema(description = "Preferencias Alimentares da pessoa precisa estar preenchido")
        Set<AlergiaAlimentarEnum> alergias,
        @Schema(description = "Método de pagamento preferido da pessoa precisa estar preenchido")
        MetodoPagamentoEnum metodoPagamentoPreferido,
        @Schema(description = "Cliente vip precisa estar preenchido")
        Boolean clienteVip,
        @Schema(description = "Notificações Ativas da pessoa precisa estar preenchido")
        Boolean notificacoesAtivas,
        @Schema(description = "Nome da pessoa precisa estar preenchido")
        @NotBlank(message = "Nome da pessoa precisa estar preenchido")
        String nome,
        @Schema(description = "Email da pessoa precisa estar preenchido")
        @NotBlank(message = "Email da pessoa precisa estar preenchido")
        String email,
        @Schema(description = "Login da pessoa precisa estar preenchido")
        @NotBlank(message = "Login da pessoa precisa estar preenchido")
        String login,
        @Schema(description = "Senha da pessoa precisa estar preenchido")
        @NotBlank(message = "Senha da pessoa precisa estar preenchido")
        String senha,
        @Valid
        List<EnderecoRequestDTO> enderecos
) {
}
