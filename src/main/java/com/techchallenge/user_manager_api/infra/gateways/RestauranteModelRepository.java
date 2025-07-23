package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteGatewayRepository;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import com.techchallenge.user_manager_api.infra.model.ProprietarioEntity;
import com.techchallenge.user_manager_api.infra.model.RestauranteEntity;
import com.techchallenge.user_manager_api.infra.persistence.adapters.RestauranteAdapter;
import com.techchallenge.user_manager_api.infra.repositories.ProprietarioRepository;
import com.techchallenge.user_manager_api.infra.repositories.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RestauranteModelRepository implements RestauranteGatewayRepository {

    private final RestauranteRepository restauranteRepository;
    private final ProprietarioRepository proprietarioRepository;

    public RestauranteModelRepository(RestauranteRepository restauranteRepository, ProprietarioRepository proprietarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.proprietarioRepository = proprietarioRepository;
    }

    @Override
    public RestauranteDomain cadastrarRestaurante(RestauranteDomain restauranteDomain) {

        ProprietarioEntity proprietarioEntity = proprietarioRepository.findById(restauranteDomain.getProprietario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Proprietário não encontrado"));

        RestauranteEntity restauranteEntity = RestauranteAdapter.toEntity(restauranteDomain, proprietarioEntity);
        restauranteRepository.save(restauranteEntity);

        return RestauranteAdapter.toRestauranteDomain(restauranteEntity);
    }

}
