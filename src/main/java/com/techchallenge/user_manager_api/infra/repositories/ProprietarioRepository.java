package com.techchallenge.user_manager_api.infra.repositories;

import com.techchallenge.user_manager_api.infra.model.ProprietarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProprietarioRepository extends JpaRepository<ProprietarioEntity, Integer> {
}
