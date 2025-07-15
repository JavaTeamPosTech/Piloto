package com.techchallenge.user_manager_api.application.usecases;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.application.usecases.presenters.ClientePresenter;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarClienteUseCase {


    private final ClienteGatewayRepository clienteRepository;

    public BuscarClienteUseCase(ClienteGatewayRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDomain buscarClientePorId(UUID id) {

        return clienteRepository.buscarClientePorId(id);
    }


}
