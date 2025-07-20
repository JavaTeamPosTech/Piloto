package com.techchallenge.user_manager_api.infra.persistence.adapters;

import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;
import com.techchallenge.user_manager_api.infra.model.TipoCozinhaEntity;

import java.util.List;

public class TipoCozinhaAdapter {

    public static List<TipoCozinhaDomain> toDomainList(List<TipoCozinhaEntity> tipos) {
        return tipos.stream()
                .map(TipoCozinhaAdapter::toDomain)
                .toList();
    }


    private static TipoCozinhaDomain toDomain(TipoCozinhaEntity entity) {
       return new TipoCozinhaDomain(entity.getId(), entity.getDescricao());
    }

}
