package com.techchallenge.user_manager_api.api.controllers.gateways;

import com.techchallenge.user_manager_api.domain.entities.EnderecoRestauranteDomain;

import java.util.UUID;

public interface EnderecoRestauranteGatewayRepository {
    EnderecoRestauranteDomain buscarPorId(UUID id);
}
