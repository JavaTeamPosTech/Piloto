package com.techchallenge.user_manager_api.infra.persistence.adapters;

import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;
import com.techchallenge.user_manager_api.infra.model.RestauranteTipoCozinhaEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestauranteTipoCozinhaAdapter {

    public static RestauranteTipoCozinhaEntity toEntity(UUID idRestaurante, UUID idTipoCozinha) {
        return new RestauranteTipoCozinhaEntity(idRestaurante, idTipoCozinha);
    }

    public static List<RestauranteTipoCozinhaEntity> toEntityList(UUID idRestaurante, List<TipoCozinhaDomain> tipoCozinhaDomains) {
        List<RestauranteTipoCozinhaEntity> restauranteTipoCozinhaEntities = new ArrayList<>();

        for (TipoCozinhaDomain tipoCozinhaDomain : tipoCozinhaDomains) {
            RestauranteTipoCozinhaEntity tipoCozinha = new RestauranteTipoCozinhaEntity(idRestaurante, tipoCozinhaDomain.getId());
            restauranteTipoCozinhaEntities.add(tipoCozinha);

        }
        return restauranteTipoCozinhaEntities;
    }


}
