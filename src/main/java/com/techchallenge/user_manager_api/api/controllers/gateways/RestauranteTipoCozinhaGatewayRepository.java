package com.techchallenge.user_manager_api.api.controllers.gateways;

import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;

import java.util.List;
import java.util.UUID;

public interface RestauranteTipoCozinhaGatewayRepository {
    void adicionarTipos(UUID id, List<TipoCozinhaDomain> tipoCozinhaDomains);

    void excluirTiposPorId(UUID id, List<TipoCozinhaDomain> tipoCozinhaDomains);

}
