package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteGatewayRepository;
import com.techchallenge.user_manager_api.application.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import com.techchallenge.user_manager_api.infra.model.RestauranteEntity;
import com.techchallenge.user_manager_api.infra.persistence.adapters.RestauranteAdapter;
import com.techchallenge.user_manager_api.infra.repositories.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestauranteModelRepository implements RestauranteGatewayRepository {

    private final RestauranteRepository restauranteRepository;

    public RestauranteModelRepository(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public RestauranteDomain cadastrarRestaurante(RestauranteDomain restauranteDomain) {

        RestauranteEntity restauranteEntity = RestauranteAdapter.toEntity(restauranteDomain);

        restauranteRepository.save(restauranteEntity);

        return RestauranteAdapter.toRestauranteDomain(restauranteEntity);
    }

    @Override
    public RestauranteDomain buscarRestaurantePorId(UUID id) {
        RestauranteEntity restaurante = findById(id);

        return RestauranteAdapter.toRestauranteDomain(restaurante);
    }

    @Override
    public void deletarRestauratePorId(UUID id) {
        RestauranteEntity restaurante = findById(id);

        restauranteRepository.delete(restaurante);
    }

    @Override
    public RestauranteDomain atualizarRestaurante(RestauranteDomain restauranteDomain) {
        RestauranteEntity restauranteEntity = RestauranteAdapter.toEntity(restauranteDomain);
        restauranteRepository.save(restauranteEntity);
        return RestauranteAdapter.toRestauranteDomain(restauranteEntity);
    }

    private RestauranteEntity findById(UUID id) {
        return restauranteRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + id));
    }
}
