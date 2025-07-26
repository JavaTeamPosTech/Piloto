package com.techchallenge.user_manager_api.application.usecases.restaurante;

import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteGatewayRepository;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class BuscarRestaurantePorIdUseCase {

    private final RestauranteGatewayRepository restauranteGatewayRepository;

    public BuscarRestaurantePorIdUseCase(RestauranteGatewayRepository restauranteGatewayRepository) {
        this.restauranteGatewayRepository = restauranteGatewayRepository;
    }

    public RestauranteDomain executar(UUID id) {
        return restauranteGatewayRepository.buscarRestaurantePorId(id);
    }
}
