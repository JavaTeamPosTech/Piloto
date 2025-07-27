package com.techchallenge.user_manager_api.domain.entities;

import java.util.Set;
import java.util.UUID;

public class RestauranteTipoCozinhaDomain {

    private UUID restauranteId;

    private Set<UUID> tipoCozinhaId;


    public RestauranteTipoCozinhaDomain (UUID restauranteId, Set<UUID> tipoCozinhaId){
        this.restauranteId = restauranteId;
        this.tipoCozinhaId = tipoCozinhaId;
    }

    public UUID getRestauranteId() {
        return restauranteId;
    }

    public Set<UUID> getTipoCozinhaId() {
        return tipoCozinhaId;
    }
}
