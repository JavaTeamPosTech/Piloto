package com.techchallenge.user_manager_api.repositories;

import com.techchallenge.user_manager_api.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLoginAndSenha(String login, String senha);

    Optional<Usuario> findByLogin(String login);
}
