package com.techchallenge.user_manager_api.domain.dto.requests;

import com.techchallenge.user_manager_api.naousar.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.naousar.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.naousar.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.naousar.entities.enums.TiposComidaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Schema(description = "DTO para criação de um Cliente")
public record ClienteRequestDTO(

        @Schema(description = "Nome da pessoa precisa estar preenchido", example = "João da Silva")
        @NotBlank(message = "Nome da pessoa precisa estar preenchido")
        String nome,

        @Schema(description = "CPF da pessoa precisa estar preenchido", example = "123.456.789-00")
        @NotBlank(message = "CPF da pessoa precisa estar preenchido")
        @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres")
        String cpf,

        @Schema(description = "Email da pessoa precisa estar preenchido", example ="joaodasilva@email.com")
        @NotBlank(message = "Email da pessoa precisa estar preenchido")
        String email,

        @Schema(description = "Login da pessoa precisa estar preenchido", example = "joaodasilva")
        @NotBlank(message = "Login da pessoa precisa estar preenchido")
        @Size(min = 4, max = 30, message = "O login deve ter entre 4 e 30 caracteres")
        String login,

        @Schema(description = "Data de nascimento da pessoa precisa estar preenchido", example = "1990-01-01")
        LocalDate dataNascimento,

        @Schema(description = "Gênero da pessoa precisa estar preenchido")
        GeneroEnum genero,

        @Schema(description = "Telefone da pessoa precisa estar preenchido", example = "+5581999992345")
        @Pattern(regexp = "\\+?[0-9]{8,15}", message = "Numero de telefone inválido")
        @NotBlank(message = "Telefone da pessoa precisa estar preenchido")
        String telefone,

        @Schema(description = "Preferencias Alimentares da pessoa precisa estar preenchido", example = "[\"VEGETARIANA\", \"SAUDAVEL\"]")
        Set<TiposComidaEnum> preferenciasAlimentares,

        @Schema(description = "Preferencias Alimentares da pessoa precisa estar preenchido", example = "[\"AMENDOIM\", \"LACTOSE\"]")
        Set<AlergiaAlimentarEnum> alergias,

        @Schema(description = "Método de pagamento preferido da pessoa precisa estar preenchido", example = "CREDITO")
        MetodoPagamentoEnum metodoPagamentoPreferido,

        @Schema(description = "Cliente vip precisa estar preenchido", example = "true")
        Boolean clienteVip,

        @Schema(description = "Notificações Ativas da pessoa precisa estar preenchido", example = "true")
        Boolean notificacoesAtivas,

        @Schema(description = "Senha da pessoa precisa estar preenchido", example = "SenhaForte123!")
        @NotBlank(message = "Senha da pessoa precisa estar preenchido")
        @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
        String senha,

        @Schema(description = "Lista de endereços do usuário (pelo menos 1)")
        @NotNull(message = "A lista de endereços não pode ser nula")
        @Size(min = 1, message = "Pelo menos um endereço deve ser informado")
        @Valid
        List<EnderecoRequestDTO> enderecos
) {
}
