package com.techchallenge.user_manager_api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record EnderecoRequestDTO(

        @Schema(description = "Estado precisa estar preenchido", example = "SP")
        @NotBlank(message = "Estado precisa estar preenchido")
        @Size(min = 2, max = 2, message = "O estado deve conter exatamente 2 caracteres")
        String estado,

        @Schema(description = "Cidade precisa estar preenchida", example = "Sao Paulo")
        @NotBlank(message = "Cidade precisa estar preenchida")
        @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
        String cidade,

        @Schema(description = "Bairro precisa estar preenchido", example = "Jardim Paulista")
        @NotBlank(message = "Bairro precisa estar preenchido")
        @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
        String bairro,

        @Schema(description = "Rua precisa estar preenchida", example = "Avenida Paulista")
        @NotBlank(message = "Rua precisa estar preenchida")
        @Size(max = 150, message = "A rua deve ter no máximo 150 caracteres")
        String rua,

        @Schema(description = "Número precisa estar preenchido", example = "123")
        @NotNull(message = "Número precisa estar preenchido")
        @Positive(message = "O número deve ser positivo")
        Integer numero,

        @Schema(description = "Complemento opcional, pode ser vazio", example = "Apt 101")
        String complemento,

        @Schema(description = "CEP precisa estar preenchido", example = "01311-000")
        @NotBlank(message = "CEP precisa estar preenchido")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000")
        String cep
) {
}
