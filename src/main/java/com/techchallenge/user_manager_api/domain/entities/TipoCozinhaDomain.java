package com.techchallenge.user_manager_api.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class TipoCozinhaDomain {

    private UUID id;
    private String descricao;

    public TipoCozinhaDomain(UUID id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

}
