package com.techchallenge.user_manager_api.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "restaurante_tipo_cozinha")
public class RestauranteTipoCozinhaEntity {

    @EmbeddedId
    private RestauranteTipoCozinhaId id;

    @ManyToOne
    @MapsId("restauranteId")
    @JoinColumn(name = "restaurante_id")
    private RestauranteEntity restaurante;

    @ManyToOne
    @MapsId("tipoCozinhaId")
    @JoinColumn(name = "tipo_cozinha_id")
    private TipoCozinhaEntity tipoCozinha;


    public RestauranteTipoCozinhaEntity(UUID idRestaurante, UUID IdTipoCozinha) {
        this.id = new RestauranteTipoCozinhaId(idRestaurante, IdTipoCozinha);
    }


    public RestauranteTipoCozinhaEntity(RestauranteEntity restaurante, TipoCozinhaEntity tipoCozinha) {
        this.restaurante = restaurante;
        this.tipoCozinha = tipoCozinha;
        this.id = new RestauranteTipoCozinhaId(
                restaurante.getId(),
                tipoCozinha.getId()
        );
    }

}
