package com.techchallenge.user_manager_api.domain.entities;

import lombok.Getter;

import java.util.List;

@Getter
public class RestauranteDomain {

    private String nome;

    private EnderecoRestauranteDomain endereco;

    private List<TipoCozinhaDomain> tiposCozinha;

    private ProprietarioDomain proprietario;

    public RestauranteDomain(String nome,  EnderecoRestauranteDomain endereco, List<TipoCozinhaDomain> tiposCozinha,
                             ProprietarioDomain proprietario) {
        this.nome = nome;
        this.endereco = endereco;
        this.tiposCozinha = tiposCozinha;
        this.proprietario = proprietario;
    }


}
