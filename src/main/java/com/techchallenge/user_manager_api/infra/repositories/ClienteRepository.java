package com.techchallenge.user_manager_api.infra.repositories;

import com.techchallenge.user_manager_api.infra.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
}
