package com.techchallenge.user_manager_api.domain.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para atualização de um Restaurante")
public record AtualizarRestauranteRequestDTO(

        @Schema(description = "Nome do restaurante", example = "Restaurante do João")
        @NotBlank(message = "O nome do restaurante precisa estar preenchido")
        @Size(min = 2, max = 100, message = "O nome do restaurante deve ter entre 2 e 100 caracteres")
        String nome,

        @Schema(description = "Endereço do restaurante")
        @NotNull(message = "O endereço do restaurante precisa estar preenchido")
        @Valid
        EnderecoRestauranteRequestDTO endereco
) {
}
