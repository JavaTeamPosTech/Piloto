package com.techchallenge.user_manager_api.application.usecases.cliente;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.application.inputs.AtualizarClienteInput;
import com.techchallenge.user_manager_api.application.usecases.presenters.ClientePresenter;
import com.techchallenge.user_manager_api.domain.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import org.springframework.stereotype.Service;

@Service
public class AtualizarClienteUseCase {



    private final ClienteGatewayRepository clienteRepository;
    private final ClientePresenter clientePresenter;

    public AtualizarClienteUseCase(ClienteGatewayRepository clienteRepository, ClientePresenter clientePresenter) {
        this.clienteRepository = clienteRepository;
        this.clientePresenter = clientePresenter;
    }


    public ClienteResponseDTO executar(AtualizarClienteInput atualizarClienteInput) {
        ClienteDomain clienteAtual = clienteRepository.buscarClientePorId(atualizarClienteInput.getId());

        boolean loginEmUso = clienteRepository.existsByLogin(atualizarClienteInput.getLogin());
        if (loginEmUso && !clienteAtual.getLogin().equals(atualizarClienteInput.getLogin())) {
            throw new IllegalArgumentException("Login já está em uso");
        }

        ClienteDomain clienteAtualizado = clienteAtual.atualizarCom(atualizarClienteInput, clienteAtual.getSenha());

        clienteRepository.alterarInformacoesDoCliente(clienteAtualizado, clienteAtualizado.getSenha());

        return clientePresenter.retornarCliente(clienteAtualizado);

    }
}
