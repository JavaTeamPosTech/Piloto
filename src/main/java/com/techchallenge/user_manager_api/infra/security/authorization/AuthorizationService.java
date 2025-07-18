package com.techchallenge.user_manager_api.infra.security.authorization;


import com.techchallenge.user_manager_api.domain.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import com.techchallenge.user_manager_api.infra.model.UsuarioEntity;

public interface AuthorizationService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    UsuarioDomain autenticar(String login, String senha);
}
