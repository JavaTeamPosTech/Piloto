package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import jakarta.validation.Valid;

public interface UsuarioService {
    void atualizarSenha(@Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO);

    void fazerLogin(@Valid LoginRequestDTO loginRequestDTO);

}
