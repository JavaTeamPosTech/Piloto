package com.techchallenge.user_manager_api.api.controllers.gateways;

import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TipoCozinhaGatewayRepository {
    List<TipoCozinhaDomain> buscarTiposPorId(Set<UUID> id);
}
