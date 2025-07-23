package com.techchallenge.user_manager_api.application.mappers;

import com.techchallenge.user_manager_api.domain.dto.requests.RestauranteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.RestauranteResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RestauranteMapper {


    public static RestauranteDomain toDomain(RestauranteRequestDTO dto, ProprietarioDomain proprietario) {

       return new RestauranteDomain(dto.nome(),  proprietario);
    }

    public static RestauranteResponseDTO toResponseDto(RestauranteDomain restauranteDomain) {
        return new RestauranteResponseDTO(
                restauranteDomain.getNome()
        );
    }
}
