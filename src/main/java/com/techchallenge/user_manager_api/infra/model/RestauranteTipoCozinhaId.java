package com.techchallenge.user_manager_api.infra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Getter
@Embeddable
public class RestauranteTipoCozinhaId {

    @Column(name = "restaurante_id")
    private UUID restauranteId;

    @Column(name = "tipo_cozinha_id")
    private UUID tipoCozinhaId;


    public RestauranteTipoCozinhaId() {}

    public RestauranteTipoCozinhaId(UUID restauranteId, UUID tipoCozinhaId) {
        this.restauranteId = restauranteId;
        this.tipoCozinhaId = tipoCozinhaId;
    }
}
