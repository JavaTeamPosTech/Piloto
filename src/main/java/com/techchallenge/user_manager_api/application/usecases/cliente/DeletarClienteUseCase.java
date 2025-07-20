package com.techchallenge.user_manager_api.application.usecases.cliente;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.application.usecases.presenters.ClientePresenter;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarClienteUseCase {

    private final ClienteGatewayRepository clienteGatewayRepository;
    private final ClientePresenter clientePresenter;

    public DeletarClienteUseCase(ClienteGatewayRepository clienteGatewayRepository, ClientePresenter clientePresenter) {
        this.clienteGatewayRepository = clienteGatewayRepository;
        this.clientePresenter = clientePresenter;
    }

    public void executar(UUID id) {
        clienteGatewayRepository.deletarClientePorId(id);
    }
}
