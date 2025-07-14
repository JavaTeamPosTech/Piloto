package com.techchallenge.user_manager_api.dto.requests;

import com.techchallenge.user_manager_api.entities.enums.TipoCozinhaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RestauranteRequestDTO(

        @Schema(description = "Nome do restaurante", example = "Restaurante da Ana")
        @NotBlank(message = "O nome do restaurante precisa estar preenchido")
        String nome,

        @Schema(description = "Tipo de cozinha", example = "BRASILEIRA")
        @NotNull(message = "O tipo de cozinha precisa estar preenchido")
        TipoCozinhaEnum tipoCozinha,

        // TODO: Deveria ter relação com outra tabela e aqui só o id
        @Schema(description = "Horário de funcionamento", example = "09:00 - 18:00")
        @NotBlank(message = "O horário de funcionamento precisa estar preenchido")
        String horarioFuncionamento,

        @Schema(description = "Endereços do restaurante")
        @NotNull(message = "A lista de endereços não pode ser nula")
        @Size(min = 1, message = "Pelo menos um endereço deve ser informado")
        @Valid
        List<EnderecoRequestDTO> enderecos,

        @Schema(description = "Nome do proprietário responsável pelo restaurante", example = "Ana Silva")
        @NotBlank(message = "O nome do proprietário precisa ser informado")
        String proprietarioNome

) {}
