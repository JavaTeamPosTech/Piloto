package com.techchallenge.user_manager_api.application.usecases.presenters;

import com.techchallenge.user_manager_api.application.mappers.RestauranteMapper;
import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.domain.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.RestauranteResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class RestaurantePresenter {

    @Getter
    private RestauranteResponseDTO viewModel;
    private final RestauranteMapper mapper;

    public RestaurantePresenter(RestauranteMapper mapper) {
        this.mapper = mapper;
    }

    public void apresentar(RestauranteDomain restauranteDomain) {
        this.viewModel = mapper.toResponseDto(restauranteDomain);
    }

    public RestauranteResponseDTO retornarRestaurante(RestauranteDomain restauranteDomain) {
        return mapper.toResponseDto(restauranteDomain);
    }
}
