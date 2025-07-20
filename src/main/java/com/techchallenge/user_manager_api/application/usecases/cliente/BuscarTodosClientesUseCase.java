package com.techchallenge.user_manager_api.application.usecases.cliente;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.application.usecases.presenters.ClientePresenter;
import com.techchallenge.user_manager_api.domain.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarTodosClientesUseCase {

    private final ClienteGatewayRepository clienteRepository;
    private final ClientePresenter clientePresenter;


    public BuscarTodosClientesUseCase(ClienteGatewayRepository clienteRepository, ClientePresenter clientePresenter) {
        this.clienteRepository = clienteRepository;
        this.clientePresenter = clientePresenter;
    }

    public List<ClienteResponseDTO> buscarClientes() {
        List<ClienteDomain> clientes = clienteRepository.buscarTodosClientes();
        return clientes.stream()
                .map(clientePresenter::retornarCliente)
                .toList();
    }
}
