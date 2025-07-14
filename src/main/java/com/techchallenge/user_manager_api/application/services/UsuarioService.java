package com.techchallenge.user_manager_api.application.services;

import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarSenhaRequestDTO;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface UsuarioService {
    void atualizarSenha(@Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO, Authentication authentication);
    boolean existsByLogin(String login);
}
