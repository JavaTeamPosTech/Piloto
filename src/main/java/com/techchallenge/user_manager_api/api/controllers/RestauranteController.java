package com.techchallenge.user_manager_api.api.controllers;

import com.techchallenge.user_manager_api.application.usecases.restaurante.CadastrarRestauranteUseCase;
import com.techchallenge.user_manager_api.domain.dto.requests.RestauranteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.RestauranteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    private final CadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    public RestauranteController (CadastrarRestauranteUseCase cadastrarRestauranteUseCase) {
        this.cadastrarRestauranteUseCase = cadastrarRestauranteUseCase;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(@RequestBody @Valid RestauranteRequestDTO dto){
        return null;
    }
}
