package com.techchallenge.user_manager_api.api.controllers.gateways;

import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;

public interface RestauranteGatewayRepository {
    RestauranteDomain cadastrarRestaurante(RestauranteDomain restauranteDomain);
}
