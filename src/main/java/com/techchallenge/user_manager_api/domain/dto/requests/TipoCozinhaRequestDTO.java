package com.techchallenge.user_manager_api.domain.dto.requests;

import java.util.Set;
import java.util.UUID;

public record TipoCozinhaRequestDTO(
        UUID idRestaurante,
        Set<UUID> idsTipoCozinha
) {
}
