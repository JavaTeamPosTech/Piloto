package com.techchallenge.user_manager_api.api.controllers.gateways;

import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import jakarta.transaction.NotSupportedException;

import java.util.UUID;

public interface RestauranteGatewayRepository  {
    RestauranteDomain cadastrarRestaurante(RestauranteDomain restauranteDomain) throws NotSupportedException;

    RestauranteDomain buscarRestaurantePorId(UUID id);

    void deletarRestauratePorId(UUID id);

    RestauranteDomain atualizarRestaurante(RestauranteDomain restauranteDomain);
}
