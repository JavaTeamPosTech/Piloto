package com.techchallenge.user_manager_api.application.usecases.tipoCozinha;

import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteGatewayRepository;
import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteTipoCozinhaGatewayRepository;
import com.techchallenge.user_manager_api.api.controllers.gateways.TipoCozinhaGatewayRepository;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import com.techchallenge.user_manager_api.domain.entities.RestauranteTipoCozinhaDomain;
import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcluirTipoCozinhaDoRestauranteUseCase {

    private final RestauranteTipoCozinhaGatewayRepository restauranteTipoCozinhaGatewayRepository;
    private final RestauranteGatewayRepository restauranteGatewayRepository;
    private final TipoCozinhaGatewayRepository tipoGatewayRepository;

    public ExcluirTipoCozinhaDoRestauranteUseCase(RestauranteTipoCozinhaGatewayRepository restauranteTipoCozinhaGatewayRepository,
                                                  RestauranteGatewayRepository restauranteGatewayRepository,
                                                  TipoCozinhaGatewayRepository tipoGatewayRepository) {
        this.restauranteTipoCozinhaGatewayRepository = restauranteTipoCozinhaGatewayRepository;
        this.restauranteGatewayRepository = restauranteGatewayRepository;
        this.tipoGatewayRepository = tipoGatewayRepository;
    }

    public void executar(RestauranteTipoCozinhaDomain domain) {
        RestauranteDomain restauranteDomain = restauranteGatewayRepository.buscarRestaurantePorId(domain.getRestauranteId());

        List<TipoCozinhaDomain> tipoCozinhaDomains = tipoGatewayRepository.buscarTiposPorId(domain.getTipoCozinhaId());

        restauranteTipoCozinhaGatewayRepository.excluirTiposPorId(restauranteDomain.getId(), tipoCozinhaDomains);

    }
}
