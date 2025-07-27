package com.techchallenge.user_manager_api.domain.entities;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class RestauranteDomain {

    private UUID id;

    private String nome;

    private EnderecoRestauranteDomain endereco;

    private List<TipoCozinhaDomain> tiposCozinha;

    private ProprietarioDomain proprietario;

    public RestauranteDomain(String nome, ProprietarioDomain proprietario) {
        this.nome = nome;
        this.proprietario = proprietario;
    }

    public RestauranteDomain(UUID id,  String nome, EnderecoRestauranteDomain endereco, List<TipoCozinhaDomain> tiposCozinha,
                             ProprietarioDomain proprietario) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tiposCozinha = tiposCozinha;
        this.proprietario = proprietario;
    }


    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do restaurante não pode ser vazio");
        }
        if (nome.length() < 3 || nome.length() > 100) {
            throw new IllegalArgumentException("O nome deve ter entre 3 e 100 caracteres");
        }
        this.nome = nome;
    }

    public void setEndereco(EnderecoRestauranteDomain endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        this.endereco = endereco;
    }

    public void setTiposCozinha(List<TipoCozinhaDomain> tiposCozinha) {
        if (tiposCozinha == null || tiposCozinha.isEmpty()) {
            throw new IllegalArgumentException("Pelo menos um tipo de cozinha deve ser informado");
        }
        this.tiposCozinha = tiposCozinha;
    }



}
