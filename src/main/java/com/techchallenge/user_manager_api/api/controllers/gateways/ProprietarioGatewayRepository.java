package com.techchallenge.user_manager_api.api.controllers.gateways;

import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import org.springframework.stereotype.Service;

public interface ProprietarioGatewayRepository {
    ProprietarioDomain cadastrarProprietario(ProprietarioDomain proprietarioDomain, String senhaCriptografada);

    boolean existsByLogin(String login);

}
