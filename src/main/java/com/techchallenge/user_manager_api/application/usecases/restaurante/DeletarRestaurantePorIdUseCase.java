package com.techchallenge.user_manager_api.application.usecases.restaurante;

import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteGatewayRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarRestaurantePorIdUseCase {

    private final RestauranteGatewayRepository restauranteGatewayRepository;

    public DeletarRestaurantePorIdUseCase(RestauranteGatewayRepository restauranteGatewayRepository) {
        this.restauranteGatewayRepository = restauranteGatewayRepository;
    }

    public void executar(UUID id) {
        restauranteGatewayRepository.deletarRestauratePorId(id);
    }
}
