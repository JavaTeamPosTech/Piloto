package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.UsuarioDTO;
import com.techchallenge.user_manager_api.entities.Usuario;
import com.techchallenge.user_manager_api.repositories.UsuarioRepository;
import com.techchallenge.user_manager_api.services.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void cadastrarUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario(dto);
        usuarioRepository.save(usuario);
    }
}
