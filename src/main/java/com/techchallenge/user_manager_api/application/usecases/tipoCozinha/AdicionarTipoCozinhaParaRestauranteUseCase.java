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
public class AdicionarTipoCozinhaParaRestauranteUseCase {

    private final TipoCozinhaGatewayRepository tipoGatewayRepository;
    private final RestauranteGatewayRepository restauranteGatewayRepository;
    private final RestauranteTipoCozinhaGatewayRepository restauranteTipoCozinhaGatewayRepository;

    public AdicionarTipoCozinhaParaRestauranteUseCase(TipoCozinhaGatewayRepository tipoGatewayRepository,
                                                      RestauranteGatewayRepository restauranteGatewayRepository,
                                                      RestauranteTipoCozinhaGatewayRepository restauranteTipoCozinhaGatewayRepository) {
        this.tipoGatewayRepository = tipoGatewayRepository;
        this.restauranteGatewayRepository = restauranteGatewayRepository;
        this.restauranteTipoCozinhaGatewayRepository = restauranteTipoCozinhaGatewayRepository;
    }

    public void executar(RestauranteTipoCozinhaDomain domain) {
        RestauranteDomain restauranteDomain = restauranteGatewayRepository.buscarRestaurantePorId(domain.getRestauranteId());

        List<TipoCozinhaDomain> tipoCozinhaDomains = tipoGatewayRepository.buscarTiposPorId(domain.getTipoCozinhaId());

        restauranteTipoCozinhaGatewayRepository.adicionarTipos(restauranteDomain.getId(), tipoCozinhaDomains);
    }
}
