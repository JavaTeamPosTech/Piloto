package com.techchallenge.user_manager_api.infra.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "endereco_restaurante")
public class EnderecoRestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
    private String cep;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    private RestauranteEntity restaurante;
}
