package com.techchallenge.user_manager_api.api.controllers.gateways;

import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;

import java.util.UUID;

public interface ProprietarioGatewayRepository {
    ProprietarioDomain cadastrarProprietario(ProprietarioDomain proprietarioDomain, String senhaCriptografada);

    boolean existsByLogin(String login);

    ProprietarioDomain buscarProprietarioPorId(UUID id);

    void deletarProprietarioPorId(UUID id);

    void atualizar(ProprietarioDomain proprietarioDomain);

}
