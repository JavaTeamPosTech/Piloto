package com.techchallenge.user_manager_api.application.usecases.restaurante;

import com.techchallenge.user_manager_api.api.controllers.gateways.EnderecoRestauranteGatewayRepository;
import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteGatewayRepository;
import com.techchallenge.user_manager_api.domain.entities.EnderecoRestauranteDomain;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import org.springframework.stereotype.Service;

@Service
public class AtualizarRestauranteUseCase {
    private final RestauranteGatewayRepository restauranteGatewayRepository;
    private final EnderecoRestauranteGatewayRepository enderecoRestauranteGatewayRepository;

    public AtualizarRestauranteUseCase(RestauranteGatewayRepository restauranteGatewayRepository,
                                       EnderecoRestauranteGatewayRepository enderecoRestauranteGatewayRepository){
        this.restauranteGatewayRepository = restauranteGatewayRepository;
        this.enderecoRestauranteGatewayRepository = enderecoRestauranteGatewayRepository;
    }

    public void executar(RestauranteDomain restauranteDomain){
        RestauranteDomain restauranteAtual = restauranteGatewayRepository.buscarRestaurantePorId(restauranteDomain.getId());

        EnderecoRestauranteDomain enderecoAtual =
                enderecoRestauranteGatewayRepository.buscarPorId(restauranteAtual.getEndereco().getId());

        enderecoAtual.atualizarEndereco(restauranteAtual.getEndereco());

        restauranteAtual.setNome(restauranteDomain.getNome());
        restauranteAtual.setEndereco(enderecoAtual);

        restauranteGatewayRepository.atualizarRestaurante(restauranteDomain);
    }

}
