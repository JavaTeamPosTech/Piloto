package com.techchallenge.user_manager_api.domain.dto.requests;

import java.util.UUID;

public record EnderecoRestauranteRequestDTO(

         String estado,
         String cidade,
         String bairro,
         String rua,
         Integer numero,
         String complemento,
         String cep,
         UUID restauranteId
) {
}
