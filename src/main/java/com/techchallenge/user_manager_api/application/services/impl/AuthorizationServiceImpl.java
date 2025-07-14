package com.techchallenge.user_manager_api.application.services.impl;

import com.techchallenge.user_manager_api.application.services.UsuarioService;
import com.techchallenge.user_manager_api.naousar.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.naousar.entities.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public AuthorizationServiceImpl(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        if (!usuarioService.existsByLogin(loginRequestDTO.login())) {
            throw new ResourceNotFoundException("Usuário com login '" + loginRequestDTO.login() + "' não encontrado.");
        }

        try  {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.login(),
                            loginRequestDTO.senha()
                    )
            );
            Usuario user = (Usuario) authentication.getPrincipal();
            return new LoginResponseDTO(tokenService.generateToken(user.getLogin()));

        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Senha incorreta.");
        }
    }
}
