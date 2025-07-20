package com.techchallenge.user_manager_api.api.controllers.gateways;

import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;

import java.util.UUID;

public interface ClienteGatewayRepository {

    ClienteDomain cadastrarCliente(ClienteDomain clienteDomain, String senhaCriptografada);

    boolean existsByLogin(String login);

    ClienteDomain buscarClientePorId(UUID id);

    ClienteDomain alterarInformacoesDoCliente(ClienteDomain entity, String senhaCriptografada);
}
