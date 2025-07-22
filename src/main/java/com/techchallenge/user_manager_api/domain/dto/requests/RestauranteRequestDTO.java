package com.techchallenge.user_manager_api.domain.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

@Schema(description = "DTO para criação de um Restaurante")
public record RestauranteRequestDTO(

        @Schema(description = "Nome do restaurante", example = "Restaurante do João")
        @NotBlank(message = "O nome do restaurante precisa estar preenchido")
        @Size(min = 2, max = 100, message = "O nome do restaurante deve ter entre 2 e 100 caracteres")
        String nome,

        @Schema(description = "Endereço do restaurante")
        @NotNull(message = "O endereço do restaurante precisa estar preenchido")
        @Valid
        EnderecoRestauranteRequestDTO endereco,

        @Schema(description = "IDs dos tipos de cozinha do restaurante", example = "[\"d290f1ee-6c54-4b01-90e6-d701748f0851\", \"4a7aabfa-62b5-4c13-b7b0-1920de7e1f7c\"]")
        @NotNull(message = "Os tipos de cozinha precisam estar preenchidos")
        @Size(min = 1, message = "Pelo menos um tipo de cozinha deve ser informado")
        Set<UUID> tiposCozinhaId,

        @Schema(description = "ID do proprietário do restaurante", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        @NotNull(message = "O ID do proprietário precisa estar preenchido")
        UUID proprietarioId

) {}
