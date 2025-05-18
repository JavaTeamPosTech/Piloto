package com.techchallenge.user_manager_api.repositories;

import com.techchallenge.user_manager_api.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByLoginAndSenha(String login, String senha);
}
