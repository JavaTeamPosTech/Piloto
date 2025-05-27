package com.techchallenge.user_manager_api.repositories;

import com.techchallenge.user_manager_api.entities.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, UUID> {

}
