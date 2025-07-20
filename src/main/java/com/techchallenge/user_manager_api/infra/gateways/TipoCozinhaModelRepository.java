package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.TipoCozinhaGatewayRepository;
import com.techchallenge.user_manager_api.application.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;
import com.techchallenge.user_manager_api.infra.model.TipoCozinhaEntity;
import com.techchallenge.user_manager_api.infra.persistence.adapters.TipoCozinhaAdapter;
import com.techchallenge.user_manager_api.infra.repositories.TipoCozinhaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TipoCozinhaModelRepository implements TipoCozinhaGatewayRepository {

    private final TipoCozinhaRepository tipoCozinhaRepository;

    public TipoCozinhaModelRepository(TipoCozinhaRepository tipoCozinhaRepository) {
        this.tipoCozinhaRepository = tipoCozinhaRepository;
    }

    @Override
    public List<TipoCozinhaDomain> buscarTiposPorId(Set<UUID> id) {
        List<TipoCozinhaEntity> tipos = tipoCozinhaRepository.findAllById(id);
        if (tipos.isEmpty()) {
            throw new ResourceNotFoundException("Não foram encontrado tipos de cozinha para os ids informados");
        }
        return TipoCozinhaAdapter.toDomainList(tipos);
    }
}
