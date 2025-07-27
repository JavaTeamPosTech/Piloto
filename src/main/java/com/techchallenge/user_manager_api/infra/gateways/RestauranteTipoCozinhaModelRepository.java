package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteTipoCozinhaGatewayRepository;
import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;
import com.techchallenge.user_manager_api.infra.model.RestauranteTipoCozinhaEntity;
import com.techchallenge.user_manager_api.infra.model.RestauranteTipoCozinhaId;
import com.techchallenge.user_manager_api.infra.persistence.adapters.RestauranteTipoCozinhaAdapter;
import com.techchallenge.user_manager_api.infra.repositories.RestauranteTipoCozinhaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestauranteTipoCozinhaModelRepository implements RestauranteTipoCozinhaGatewayRepository {

    private final RestauranteTipoCozinhaRepository restauranteTipoCozinhaRepository;

    public RestauranteTipoCozinhaModelRepository(RestauranteTipoCozinhaRepository restauranteTipoCozinhaRepository) {
        this.restauranteTipoCozinhaRepository = restauranteTipoCozinhaRepository;
    }


    @Override
    public void adicionarTipos(UUID restauranteId, List<TipoCozinhaDomain> tipoCozinhaDomains) {

        List<RestauranteTipoCozinhaEntity> entidades = RestauranteTipoCozinhaAdapter.toEntityList(restauranteId, tipoCozinhaDomains);

        List<RestauranteTipoCozinhaId> ids = entidades.stream()
                .map(RestauranteTipoCozinhaEntity::getId)
                .toList();

        List<RestauranteTipoCozinhaEntity> existentes = restauranteTipoCozinhaRepository.findAllById(ids);

        if (!existentes.isEmpty()) {
            List<UUID> tiposDuplicados = existentes.stream()
                    .map(e -> e.getId().getTipoCozinhaId())
                    .toList();

            List<String> descricoesDuplicadas = tipoCozinhaDomains.stream()
                    .filter(tc -> tiposDuplicados.contains(tc.getId()))
                    .map(TipoCozinhaDomain::getDescricao)
                    .toList();

            throw new IllegalArgumentException("Os seguintes tipos de cozinha já estão vinculados ao restaurante: "
                    + String.join(", ", descricoesDuplicadas));
        }

        restauranteTipoCozinhaRepository.saveAll(entidades);

    }

    @Override
    public void excluirTiposPorId(UUID id, List<TipoCozinhaDomain> tipoCozinhaDomains) {
        List<RestauranteTipoCozinhaEntity> entidades = RestauranteTipoCozinhaAdapter.toEntityList(id, tipoCozinhaDomains);

        List<RestauranteTipoCozinhaId> ids = entidades.stream()
                .map(RestauranteTipoCozinhaEntity::getId)
                .toList();

        restauranteTipoCozinhaRepository.deleteAllById(ids);
    }
}
