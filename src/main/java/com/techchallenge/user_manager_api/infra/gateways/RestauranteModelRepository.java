package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteGatewayRepository;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import com.techchallenge.user_manager_api.infra.repositories.RestauranteRepository;
import org.springframework.stereotype.Service;

@Service
public class RestauranteModelRepository implements RestauranteGatewayRepository {

    private final RestauranteRepository restauranteRepository;

    public RestauranteModelRepository(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public void cadastrarRestaurante(RestauranteDomain restauranteDomain) {
        //RestauranteAda
        //restauranteRepository.save()
    }
}
