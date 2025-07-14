package com.techchallenge.user_manager_api.infra.security.authorization;

import com.techchallenge.user_manager_api.naousar.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.LoginResponseDTO;

public interface AuthorizationService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

}
