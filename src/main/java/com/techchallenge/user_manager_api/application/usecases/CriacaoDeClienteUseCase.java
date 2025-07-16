package com.techchallenge.user_manager_api.application.usecases;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.domain.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.infra.security.encrypt.PasswordService;
import com.techchallenge.user_manager_api.infra.security.token.TokenService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CriacaoDeClienteUseCase {

    private final ClienteGatewayRepository repositorio;
    private final PasswordService passwordService;
    private final TokenService tokenService;
    private final ClientePresenter clientePresenter;

    public CriacaoDeClienteUseCase(ClienteGatewayRepository repositorio, PasswordService passwordService, TokenService tokenService, ClientePresenter clientePresenter) {
        this.repositorio = repositorio;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
        this.clientePresenter = clientePresenter;
    }

    public void cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {

        if (repositorio.existsByLogin(clienteRequestDTO.login())) {
            throw new DataIntegrityViolationException("uk_usuario_login:  O login '" + clienteRequestDTO.login() + "' já está em uso.");
        }
        String senhaCriptografada = passwordService.encryptPassword(clienteRequestDTO.senha());

        ClienteDomain clienteDomain = UsuarioMapper.toClienteDomain(clienteRequestDTO, senhaCriptografada);
        repositorio.cadastrarCliente(clienteDomain, senhaCriptografada);
        //String token = tokenService.generateToken(clienteDomain.getLogin());
        clientePresenter.apresentar(clienteDomain);
    }

}
