package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.LoginResponseDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.entities.Usuario;
import com.techchallenge.user_manager_api.exceptions.UnauthorizedException;
import com.techchallenge.user_manager_api.repositories.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    private final UsuarioRepository usuarioRepository;

    public AuthorizationServiceImpl(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
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
            throw new UnauthorizedException("Login ou senha incorretos");
        }
    }
}
