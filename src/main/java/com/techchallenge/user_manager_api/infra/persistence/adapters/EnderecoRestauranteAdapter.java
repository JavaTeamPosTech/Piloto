package com.techchallenge.user_manager_api.infra.persistence.adapters;

import com.techchallenge.user_manager_api.domain.entities.EnderecoRestauranteDomain;
import com.techchallenge.user_manager_api.infra.model.EnderecoRestauranteEntity;

public class EnderecoRestauranteAdapter {

    public static EnderecoRestauranteDomain toDomain(EnderecoRestauranteEntity entity){
        return new EnderecoRestauranteDomain(entity.getId(), entity.getEstado(), entity.getCidade(), entity.getBairro(),
                entity.getRua(), entity.getNumero(), entity.getComplemento(), entity.getCep());
    }
}
