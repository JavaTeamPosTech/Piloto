package com.techchallenge.user_manager_api.application.usecases.usuario;

import com.techchallenge.user_manager_api.api.controllers.gateways.UsuarioGatewayRepository;
import com.techchallenge.user_manager_api.application.exceptions.UnauthorizedException;
import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.domain.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import com.techchallenge.user_manager_api.infra.persistence.adapters.UsuarioAdapter;
import com.techchallenge.user_manager_api.infra.security.authorization.AuthorizationService;
import com.techchallenge.user_manager_api.infra.security.token.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final UsuarioGatewayRepository repositorio;
    private final TokenService tokenService;
    private final AuthorizationService authorizationService;
    private final UsuarioPresenter usuarioPresenter;

    public LoginUseCase(AuthenticationManager authenticationManager, UsuarioGatewayRepository repositorio, TokenService tokenService, AuthorizationService authorizationService, UsuarioPresenter usuarioPresenter) {
        this.authenticationManager = authenticationManager;
        this.repositorio = repositorio;
        this.tokenService = tokenService;
        this.authorizationService = authorizationService;
        this.usuarioPresenter = usuarioPresenter;
    }

    public void login(LoginRequestDTO loginRequestDTO) {

        try {
            UsuarioDomain usuarioDomain = authorizationService.autenticar(loginRequestDTO.login(), loginRequestDTO.senha());
            var token = tokenService.generateToken(usuarioDomain.getLogin());

            usuarioPresenter.apresentar(token);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Senha incorreta.");
        }
    }
}
