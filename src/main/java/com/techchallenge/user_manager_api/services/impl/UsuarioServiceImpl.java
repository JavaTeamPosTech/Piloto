package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.LoginRequestDTO;
import com.techchallenge.user_manager_api.entities.Usuario;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.exceptions.UnauthorizedException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Login ou senha incorreto"));
    }

    @Override
    public void atualizarSenha(Long id, AtualizarSenhaRequestDTO atualizarSenhaDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        if (usuario.getSenha().equals(atualizarSenhaDTO.senhaAtual()) && usuario.getLogin().equals(atualizarSenhaDTO.login())) {
            usuario.atualizarSenha(atualizarSenhaDTO.novaSenha());
            usuarioRepository.save(usuario);
        } else {
            throw new UnauthorizedException("Acesso negado. Login ou senha incorreta");
        }
    }
}
