package com.techchallenge.user_manager_api.application.services.impl;

import com.techchallenge.user_manager_api.application.exceptions.UnauthorizedException;
import com.techchallenge.user_manager_api.application.services.UsuarioService;
import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.infra.model.UsuarioEntity;
import com.techchallenge.user_manager_api.infra.repositories.UsuarioRepository;
import com.techchallenge.user_manager_api.infra.security.encrypt.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordService passwordService;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordService passwordService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordService = passwordService;
    }

    @Override
    public void atualizarSenha(AtualizarSenhaRequestDTO atualizarSenhaDTO, Authentication authentication) {
        UsuarioEntity usuario = (UsuarioEntity) authentication.getPrincipal();

        if (!passwordService.matches(atualizarSenhaDTO.senhaAtual(), usuario.getSenha())) {
            throw new UnauthorizedException("Senha atual incorreta");
        }
        usuario.atualizarSenha(passwordService.encryptPassword(atualizarSenhaDTO.novaSenha()));
        usuarioRepository.save(usuario);
    }

    @Override
    public boolean existsByLogin(String login) {
        return usuarioRepository.existsByLogin(login);
    }
}
