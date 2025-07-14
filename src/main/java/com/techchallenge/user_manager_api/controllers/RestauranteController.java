package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.services.RestauranteService;
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
@RequestMapping("/restaurante")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Operation(
            summary = "Realiza o cadastro de um novo Restaurante",
            description = "Este endpoint cria um novo registro de Restaurante no sistema"
    )
    @PostMapping
    public ResponseEntity<CadastroResponseDTO> cadastrarProprietario(@RequestBody @Valid ProprietarioRequestDTO proprietarioDTO) {
        CadastroResponseDTO cadastroResponse = restauranteService.cadastrarProprietario(proprietarioDTO);
        return ResponseEntity.ok(cadastroResponse);
    }


}
