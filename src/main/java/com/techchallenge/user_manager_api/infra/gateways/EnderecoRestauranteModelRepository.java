package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.EnderecoRestauranteGatewayRepository;
import com.techchallenge.user_manager_api.infra.repositories.EnderecoRestauranteRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoRestauranteModelRepository implements EnderecoRestauranteGatewayRepository {


    private final EnderecoRestauranteRepository  enderecoRestauranteRepository;

    public EnderecoRestauranteModelRepository(EnderecoRestauranteRepository restauranteRepository) {
        this.enderecoRestauranteRepository = restauranteRepository;
    }


}
