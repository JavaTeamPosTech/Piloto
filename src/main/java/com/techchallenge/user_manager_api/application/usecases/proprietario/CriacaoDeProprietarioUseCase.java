package com.techchallenge.user_manager_api.application.usecases.proprietario;

import com.techchallenge.user_manager_api.api.controllers.gateways.ProprietarioGatewayRepository;
import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.domain.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import com.techchallenge.user_manager_api.infra.security.encrypt.PasswordService;
import com.techchallenge.user_manager_api.infra.security.token.TokenService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CriacaoDeProprietarioUseCase {

    private final ProprietarioGatewayRepository repositorio;
    private final PasswordService passwordService;
    private final TokenService tokenService;
    private final ProprietarioPresenter proprietarioPresenter;

    public CriacaoDeProprietarioUseCase(ProprietarioGatewayRepository repositorio, PasswordService passwordService, TokenService tokenService, ProprietarioPresenter proprietarioPresenter) {
        this.repositorio = repositorio;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
        this.proprietarioPresenter = proprietarioPresenter;
    }

    public void cadastrarProprietario(ProprietarioRequestDTO proprietarioRequestDTO) {

        if (repositorio.existsByLogin(proprietarioRequestDTO.login())) {
            throw new DataIntegrityViolationException("uk_usuario_login:  O login '" + proprietarioRequestDTO.login() + "' já está em uso.");
        }
        String senhaCriptografada = passwordService.encryptPassword(proprietarioRequestDTO.senha());

        ProprietarioDomain proprietarioDomain = UsuarioMapper.toProprietarioDomain(proprietarioRequestDTO, senhaCriptografada);
        ProprietarioDomain proprietarioDomainSalvo = repositorio.cadastrarProprietario(proprietarioDomain, senhaCriptografada);
        //String token = tokenService.generateToken(clienteDomain.getLogin());
        proprietarioPresenter.apresentar(proprietarioDomainSalvo);
    }

}
