package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;

public class ClienteModelRepository implements ClienteGatewayRepository {

    @Override
    public CadastroResponseDTO cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
        return null;
    }

    @Override
    public boolean existsByLogin(String login) {
        return false;
    }
}
