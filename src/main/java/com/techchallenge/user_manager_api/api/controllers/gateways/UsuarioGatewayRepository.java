package com.techchallenge.user_manager_api.api.controllers.gateways;

import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import com.techchallenge.user_manager_api.infra.model.UsuarioEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioGatewayRepository {

    boolean existsByLogin(String login);

    UsuarioDomain findByLogin(String login);

    void atualizarSenha(String login, String senhaCriptografada);

}
