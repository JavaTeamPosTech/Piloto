package com.techchallenge.user_manager_api.domain.dto.requests;

import java.util.Set;
import java.util.UUID;

public record RestauranteRequestDTO (

         String nome,
         EnderecoRestauranteRequestDTO endereco,
         Set<UUID> tiposCozinhaId,
         UUID proprietarioId
) {
}
