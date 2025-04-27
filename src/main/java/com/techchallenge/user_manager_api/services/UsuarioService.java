package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.AtualizarUsuarioRequestDTO;
import com.techchallenge.user_manager_api.dto.UsuarioRequestDTO;

public interface UsuarioService {
    void cadastrarUsuario(UsuarioRequestDTO usuario);

    void removerUsuario(Long id);

    void alterarUsuario(AtualizarUsuarioRequestDTO usuario, Long id);
}
