package com.techchallenge.user_manager_api.infra.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tipo_cozinha")
public class TipoCozinhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String descricao;
}
