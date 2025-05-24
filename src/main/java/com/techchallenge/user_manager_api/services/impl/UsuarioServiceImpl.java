package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.entities.Usuario;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
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
    public void fazerLogin(LoginRequestDTO loginRequestDTO) {
        usuarioRepository.findByLoginAndSenha(loginRequestDTO.login(), loginRequestDTO.senha())
                .orElseThrow(() -> new ResourceNotFoundException("Login ou senha incorreta"));
    }

    @Override
    public void atualizarSenha(AtualizarSenhaRequestDTO atualizarSenhaDTO) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(atualizarSenhaDTO.login(), atualizarSenhaDTO.senhaAtual())
                .orElseThrow(() -> new ResourceNotFoundException("Login ou senha incorreta"));

        usuario.atualizarSenha(atualizarSenhaDTO.novaSenha());
        usuarioRepository.save(usuario);

    }
}
