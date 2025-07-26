package com.techchallenge.user_manager_api.application.usecases.presenters;

import com.techchallenge.user_manager_api.application.mappers.RestauranteMapper;
import com.techchallenge.user_manager_api.domain.dto.response.RestauranteResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class RestaurantePresenter {

    @Getter
    private RestauranteResponseDTO viewModel;

    public RestaurantePresenter() {
    }

    public void apresentar(RestauranteDomain restauranteDomain) {
        this.viewModel = RestauranteMapper.toResponseDto(restauranteDomain);
    }

    public static RestauranteResponseDTO retornarRestaurante(RestauranteDomain restauranteDomain) {
        return RestauranteMapper.toResponseDto(restauranteDomain);
    }
}
