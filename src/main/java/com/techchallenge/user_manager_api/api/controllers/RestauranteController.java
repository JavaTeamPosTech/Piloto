package com.techchallenge.user_manager_api.api.controllers;

import com.techchallenge.user_manager_api.application.usecases.presenters.RestaurantePresenter;
import com.techchallenge.user_manager_api.application.usecases.restaurante.CadastrarRestauranteUseCase;
import com.techchallenge.user_manager_api.domain.dto.requests.RestauranteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.RestauranteResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Restaurante Controller", description = "Operações relacionadas ao Restaurante")
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final CadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    private final RestaurantePresenter restaurantePresenter;

    public RestauranteController (CadastrarRestauranteUseCase cadastrarRestauranteUseCase,
                                  RestaurantePresenter restaurantePresenter) {
        this.cadastrarRestauranteUseCase = cadastrarRestauranteUseCase;
        this.restaurantePresenter = restaurantePresenter;

    }

    @Operation(
            summary = "Realiza o cadastro de um novo Restaurante",
            description = "Este endpoint cria um novo restaurante no sistema."
    )
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(@RequestBody @Valid RestauranteRequestDTO restauranteRequestDTO) {
        RestauranteDomain restauranteDomain = cadastrarRestauranteUseCase.executar(restauranteRequestDTO);
        return ResponseEntity.ok(restaurantePresenter.toViewModel(restauranteDomain));


    }
}
