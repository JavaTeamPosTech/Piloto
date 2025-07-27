package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.EnderecoRestauranteGatewayRepository;
import com.techchallenge.user_manager_api.application.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.domain.entities.EnderecoRestauranteDomain;
import com.techchallenge.user_manager_api.infra.model.EnderecoRestauranteEntity;
import com.techchallenge.user_manager_api.infra.persistence.adapters.EnderecoRestauranteAdapter;
import com.techchallenge.user_manager_api.infra.repositories.EnderecoRestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnderecoRestauranteModelRepository implements EnderecoRestauranteGatewayRepository {


    private final EnderecoRestauranteRepository  enderecoRestauranteRepository;

    public EnderecoRestauranteModelRepository(EnderecoRestauranteRepository restauranteRepository) {
        this.enderecoRestauranteRepository = restauranteRepository;
    }

    @Override
    public EnderecoRestauranteDomain buscarPorId(UUID id){
        EnderecoRestauranteEntity enderecoRestauranteEntity = enderecoRestauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço restaurante não encontrado: " + id));

        return EnderecoRestauranteAdapter.toDomain(enderecoRestauranteEntity);
    }


}
