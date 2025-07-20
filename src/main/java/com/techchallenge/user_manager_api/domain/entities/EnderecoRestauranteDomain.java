package com.techchallenge.user_manager_api.domain.entities;

import lombok.Getter;

@Getter
public class EnderecoRestauranteDomain {


    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
    private String cep;

    private RestauranteDomain restaurante;


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
}
