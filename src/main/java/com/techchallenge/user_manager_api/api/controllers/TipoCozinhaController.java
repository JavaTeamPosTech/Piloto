package com.techchallenge.user_manager_api.api.controllers;

import com.techchallenge.user_manager_api.application.mappers.RestauranteTipoCozinhaMapper;
import com.techchallenge.user_manager_api.application.usecases.tipoCozinha.AdicionarTipoCozinhaParaRestauranteUseCase;
import com.techchallenge.user_manager_api.application.usecases.tipoCozinha.ExcluirTipoCozinhaDoRestauranteUseCase;
import com.techchallenge.user_manager_api.domain.dto.requests.TipoCozinhaRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.RestauranteTipoCozinhaDomain;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tipoCozinha")
@RestController
public class TipoCozinhaController {

    private final AdicionarTipoCozinhaParaRestauranteUseCase  adicionarTipoCozinhaParaRestauranteUseCase;
    private final ExcluirTipoCozinhaDoRestauranteUseCase  excluirTipoCozinhaDoRestauranteUseCase;


    public TipoCozinhaController(AdicionarTipoCozinhaParaRestauranteUseCase  adicionarTipoCozinhaParaRestauranteUseCase,
                                 ExcluirTipoCozinhaDoRestauranteUseCase  excluirTipoCozinhaDoRestauranteUseCase) {
        this.adicionarTipoCozinhaParaRestauranteUseCase = adicionarTipoCozinhaParaRestauranteUseCase;
        this.excluirTipoCozinhaDoRestauranteUseCase =  excluirTipoCozinhaDoRestauranteUseCase;
    }


    @PostMapping("/adicionarParaRestaurante")
    public ResponseEntity<String> adicionarTipo(@RequestBody @Valid TipoCozinhaRequestDTO dto) {
        RestauranteTipoCozinhaDomain domain = RestauranteTipoCozinhaMapper.toDomain(dto);
        adicionarTipoCozinhaParaRestauranteUseCase.executar(domain);
        return ResponseEntity.ok("Os tipos de cozinha foram adicionados com sucesos");
    }

    @DeleteMapping()
    public ResponseEntity<String> excluirTipo(@RequestBody @Valid TipoCozinhaRequestDTO dto) {
        RestauranteTipoCozinhaDomain domain = RestauranteTipoCozinhaMapper.toDomain(dto);
        excluirTipoCozinhaDoRestauranteUseCase.executar(domain);
        return ResponseEntity.ok("Tipos deletados com sucesso");
    }



}
