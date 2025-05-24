package com.techchallenge.user_manager_api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequestDTO(

        @Schema(description = "Estado precisa estar preenchido")
        @NotBlank(message = "Estado precisa estar preenchido")
         String estado,
        @Schema(description = "Cidade precisa estar preenchida")
        @NotBlank(message = "Cidade precisa estar preenchida")
         String cidade,
        @Schema(description = "Bairro precisa estar preenchido")
        @NotBlank(message = "Bairro precisa estar preenchido")
         String bairro,
        @Schema(description = "Rua precisa estar preenchida")
        @NotBlank(message = "Rua precisa estar preenchida")
         String rua,
        @Schema(description = "Número precisa estar preenchido")
        @NotNull(message = "Número precisa estar preenchido")
        @Min(value = 1, message = "Número deve ser maior que zero")
        Integer numero,
        String complemento,
        @Schema(description = "CEP precisa estar preenchido")
        @NotBlank(message = "CEP precisa estar preenchido")
         String cep
) {
}
