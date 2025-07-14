package com.techchallenge.user_manager_api.api.controllers.gateways;

import org.springframework.stereotype.Repository;

@Repository
public interface ClienteGatewayRepository {

    CadastroResponseDTO cadastrarCliente(ClienteRequestDTO clienteRequestDTO);

    boolean existsByLogin(String login);

}
