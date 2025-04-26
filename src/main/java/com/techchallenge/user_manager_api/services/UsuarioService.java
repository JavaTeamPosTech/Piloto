package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.AtualizarUsuarioRequestDTO;
import com.techchallenge.user_manager_api.dto.UsuarioDTO;
import com.techchallenge.user_manager_api.dto.UsuarioLoginDTO;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    void cadastrarUsuario(UsuarioDTO usuario);

    void removerUsuario(Long id);

    void alterarUsuario(AtualizarUsuarioRequestDTO usuario, Long id);

    Boolean logar(UsuarioLoginDTO usuario);
}
