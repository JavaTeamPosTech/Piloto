package com.techchallenge.user_manager_api.infra.repositories;

import com.techchallenge.user_manager_api.infra.model.TipoCozinhaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TipoCozinhaRepository extends JpaRepository<TipoCozinhaEntity, UUID> {
}
