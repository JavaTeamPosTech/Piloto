package com.techchallenge.user_manager_api.infra.persistence.adapters;

import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;
import com.techchallenge.user_manager_api.infra.model.RestauranteTipoCozinhaEntity;
import com.techchallenge.user_manager_api.infra.model.RestauranteTipoCozinhaId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestauranteTipoCozinhaAdapter {


    public static List<RestauranteTipoCozinhaEntity> toEntityList(UUID idRestaurante, List<TipoCozinhaDomain> tipoCozinhaDomains) {
        List<RestauranteTipoCozinhaEntity> restauranteTipoCozinhaEntities = new ArrayList<>();

        for (TipoCozinhaDomain tipoCozinhaDomain : tipoCozinhaDomains) {
            RestauranteTipoCozinhaEntity tipoCozinha = new RestauranteTipoCozinhaEntity(idRestaurante, tipoCozinhaDomain.getId());
            restauranteTipoCozinhaEntities.add(tipoCozinha);

        }
        return restauranteTipoCozinhaEntities;
    }

    public static List<RestauranteTipoCozinhaId> toRestauranteTipoCozinhaIdList(UUID idRestaurante, List<TipoCozinhaDomain> tipoCozinhaDomains) {
        List<RestauranteTipoCozinhaId> ids = new ArrayList<>();

        for (TipoCozinhaDomain tipoCozinhaDomain : tipoCozinhaDomains) {
            RestauranteTipoCozinhaId tipoCozinha = new RestauranteTipoCozinhaId(idRestaurante, tipoCozinhaDomain.getId());
            ids.add(tipoCozinha);

        }
        return ids;
    }

}
