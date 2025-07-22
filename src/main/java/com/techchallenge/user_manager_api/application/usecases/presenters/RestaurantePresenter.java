package com.techchallenge.user_manager_api.application.usecases.presenters;

import com.techchallenge.user_manager_api.domain.dto.response.RestauranteResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import org.springframework.stereotype.Service;

@Service
public class RestaurantePresenter {


    public RestauranteResponseDTO toViewModel(RestauranteDomain restaurante) {
        return new RestauranteResponseDTO(restaurante.getNome());
    }
}
