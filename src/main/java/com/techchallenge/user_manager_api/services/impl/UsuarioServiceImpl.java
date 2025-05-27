package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.entities.Usuario;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.exceptions.UnauthorizedException;
import com.techchallenge.user_manager_api.repositories.UsuarioRepository;
import com.techchallenge.user_manager_api.services.PasswordService;
import com.techchallenge.user_manager_api.services.UsuarioService;
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
    public void login(LoginRequestDTO loginRequestDTO) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(loginRequestDTO.login(), null)
                .orElseThrow(() -> new ResourceNotFoundException("Login ou senha incorreta"));

        boolean isLoginValid = passwordService.matches(loginRequestDTO.senha(), usuario.getSenha());
        if (!isLoginValid) {
            throw new UnauthorizedException("Senha incorreta");
        }
    }

    @Override
    public void atualizarSenha(AtualizarSenhaRequestDTO atualizarSenhaDTO, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        if (!passwordService.matches(atualizarSenhaDTO.senhaAtual(), usuario.getSenha())) {
            throw new UnauthorizedException("Senha atual incorreta");
        }
        usuario.atualizarSenha(passwordService.encryptPassword(atualizarSenhaDTO.novaSenha()));
        usuarioRepository.save(usuario);
    }
}
