package com.techchallenge.user_manager_api.application.usecases.usuario;

import com.techchallenge.user_manager_api.api.controllers.gateways.UsuarioGatewayRepository;
import com.techchallenge.user_manager_api.application.exceptions.UnauthorizedException;
import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import com.techchallenge.user_manager_api.infra.security.authorization.AuthorizationService;
import com.techchallenge.user_manager_api.infra.security.encrypt.PasswordService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AtualizarSenhaUseCase {

    private final UsuarioPresenter presenter;
    private final UsuarioGatewayRepository repositorio;
    private final AuthorizationService authorizationService;
    private final PasswordService passwordService;


    public AtualizarSenhaUseCase(UsuarioPresenter presenter, UsuarioGatewayRepository repositorio, AuthorizationService authorizationService, PasswordService passwordService, UsuarioMapper usuarioMapper) {
        this.presenter = presenter;
        this.repositorio = repositorio;
        this.authorizationService = authorizationService;
        this.passwordService = passwordService;
    }

    public void atualizarSenha(AtualizarSenhaRequestDTO atualizarSenhaDTO, Authentication authentication) {
        //UsuarioEntity usuario = (UsuarioEntity) authentication.getPrincipal();
        String login = authentication.getName();
        UsuarioDomain usuario = repositorio.findByLogin(login);

        if (!passwordService.matches(atualizarSenhaDTO.senhaAtual(), usuario.getSenha())) {
            throw new UnauthorizedException("Senha atual incorreta");
        }

        String novaSenhaCriptografada = passwordService.encryptPassword(atualizarSenhaDTO.novaSenha());
        repositorio.atualizarSenha(usuario.getLogin(), novaSenhaCriptografada);


//        usuario.atualizarSenha(passwordService.encryptPassword(atualizarSenhaDTO.novaSenha()));
//        usuarioRepository.save(usuario);
//
//
//        UsuarioDomain usuarioDomain = authorizationService.autenticar(login, loginRequestDTO.senha());
//
//
//
//
//        if (!repositorio.existsByLogin(login)) {
//            throw new IllegalArgumentException("Usuário não encontrado.");
//        }
//
//        var usuario = repositorio.findByLogin(login);
//
//        if (!usuario.getSenha().equals(senhaAtual)) {
//            throw new IllegalArgumentException("Senha atual incorreta.");
//        }
//
//        usuario.setSenha(novaSenha);
//        repositorio.save(usuario);
//
//        presenter.apresentar("Senha atualizada com sucesso.");
    }

}
