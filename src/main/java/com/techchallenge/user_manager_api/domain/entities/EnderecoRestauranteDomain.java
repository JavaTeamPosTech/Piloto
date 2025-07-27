package com.techchallenge.user_manager_api.domain.entities;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EnderecoRestauranteDomain {

    private UUID id;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
    private String cep;

    private RestauranteDomain restaurante;

    public EnderecoRestauranteDomain() {}

    public EnderecoRestauranteDomain(String estado, String cidade, String bairro, String rua, Integer numero, String complemento, String cep) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
    }


    public EnderecoRestauranteDomain cadastrarEndereco(String estado, String cidade, String bairro, String rua,
                                                       Integer numero, String complemento, String cep) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;

        return this;
    }

    public EnderecoRestauranteDomain(UUID id, String estado, String cidade, String bairro, String rua, Integer numero, String complemento, String cep) {
        this.id = id;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
    }

    public void atualizarEndereco(EnderecoRestauranteDomain enderecoRestaurante){
        this.estado = enderecoRestaurante.getEstado();
        this.cidade = enderecoRestaurante.getCidade();
        this.bairro = enderecoRestaurante.getBairro();
        this.rua = enderecoRestaurante.getRua();
        this.numero = enderecoRestaurante.getNumero();
        this.complemento = enderecoRestaurante.getComplemento();
        this.cep = enderecoRestaurante.getCep();
    }
}
