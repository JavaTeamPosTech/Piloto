package com.techchallenge.user_manager_api.application.mappers;

import com.techchallenge.user_manager_api.domain.dto.requests.TipoCozinhaRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.RestauranteTipoCozinhaDomain;

public class RestauranteTipoCozinhaMapper {

    public static RestauranteTipoCozinhaDomain toDomain(TipoCozinhaRequestDTO dto){
        return new  RestauranteTipoCozinhaDomain(dto.idRestaurante(), dto.idsTipoCozinha());
    }
}
