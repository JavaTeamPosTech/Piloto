package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.UsuarioDTO;

public interface UsuarioService {
    void cadastrarUsuario(UsuarioDTO usuario);

    void removerUsuario(Long id);
}
