package com.techchallenge.user_manager_api.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tipo_cozinha")
public class TipoCozinhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String descricao;

    public TipoCozinhaEntity(UUID id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
}
