package com.techchallenge.user_manager_api.application.mappers;

import com.techchallenge.user_manager_api.domain.dto.requests.EnderecoRestauranteRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.EnderecoRestauranteDomain;


public class EnderecoRestauranteMapper {

    public static EnderecoRestauranteDomain toDomain(EnderecoRestauranteRequestDTO dto) {
        return new EnderecoRestauranteDomain().cadastrarEndereco(dto.estado(), dto.cidade(), dto.bairro(), dto.rua(),
                dto.numero(), dto.complemento(), dto.cep());
    }
}
