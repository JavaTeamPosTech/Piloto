package com.techchallenge.user_manager_api.application.mappers;

import com.techchallenge.user_manager_api.domain.dto.requests.RestauranteRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.*;

import java.util.List;
import java.util.Set;

public class RestauranteMapper {


    public static RestauranteDomain toDomain(RestauranteRequestDTO dto, EnderecoRestauranteDomain endereco,
                                             List<TipoCozinhaDomain> tipoCozinhas, ProprietarioDomain proprietario) {

       return new RestauranteDomain(dto.nome(),endereco, tipoCozinhas,  proprietario);

    }
}
