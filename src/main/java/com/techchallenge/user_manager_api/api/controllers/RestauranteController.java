package com.techchallenge.user_manager_api.api.controllers;

import com.techchallenge.user_manager_api.application.usecases.presenters.RestaurantePresenter;
import com.techchallenge.user_manager_api.application.usecases.restaurante.BuscarRestaurantePorIdUseCase;
import com.techchallenge.user_manager_api.application.usecases.restaurante.CadastrarRestauranteUseCase;
import com.techchallenge.user_manager_api.application.usecases.restaurante.DeletarRestaurantePorIdUseCase;
import com.techchallenge.user_manager_api.domain.dto.requests.RestauranteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.RestauranteResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Restaurante Controller", description = "Operações relacionadas ao Restaurante")
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final CadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    private final RestaurantePresenter restaurantePresenter;
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final DeletarRestaurantePorIdUseCase deletarRestaurantePorIdUseCase;

    public RestauranteController(CadastrarRestauranteUseCase cadastrarRestauranteUseCase,
                                 RestaurantePresenter restaurantePresenter, BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase,
                                 DeletarRestaurantePorIdUseCase deletarRestaurantePorIdUseCase) {
        this.cadastrarRestauranteUseCase = cadastrarRestauranteUseCase;
        this.restaurantePresenter = restaurantePresenter;
        this.buscarRestaurantePorIdUseCase = buscarRestaurantePorIdUseCase;
        this.deletarRestaurantePorIdUseCase = deletarRestaurantePorIdUseCase;

    }

    @Operation(
            summary = "Realiza o cadastro de um novo Restaurante",
            description = "Este endpoint cria um novo restaurante no sistema."
    )
    //@PreAuthorize("hasRole('PROPRIETARIO')")
    //@SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(@RequestBody @Valid RestauranteRequestDTO restauranteRequestDTO) {
        cadastrarRestauranteUseCase.executar(restauranteRequestDTO);
        return ResponseEntity.ok(restaurantePresenter.getViewModel());
        //return ResponseEntity.ok(restaurantePresenter.toViewModel(restauranteDomain));
    }


    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable UUID id) {
        RestauranteDomain domain = buscarRestaurantePorIdUseCase.executar(id);
        RestauranteResponseDTO dto = RestaurantePresenter.retornarRestaurante(domain);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarRestaurantePorId(@PathVariable UUID id){
        deletarRestaurantePorIdUseCase.executar(id);
        return ResponseEntity.ok("Restaurante deletado com sucesso!");
    }


}
