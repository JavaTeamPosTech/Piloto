package com.techchallenge.user_manager_api.repositories;

import com.techchallenge.user_manager_api.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

}
