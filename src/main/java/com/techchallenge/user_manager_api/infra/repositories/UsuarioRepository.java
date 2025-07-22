package com.techchallenge.user_manager_api.infra.repositories;

import com.techchallenge.user_manager_api.infra.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Integer> {

    UsuarioEntity findByLogin(String login);

    boolean existsByLogin(String login);
}
