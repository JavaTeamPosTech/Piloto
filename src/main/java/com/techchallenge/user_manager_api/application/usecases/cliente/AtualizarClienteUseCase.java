package com.techchallenge.user_manager_api.application.usecases.cliente;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.application.usecases.presenters.ClientePresenter;
import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarClienteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarClienteUseCase {



    private final ClienteGatewayRepository clienteRepository;
    private final ClientePresenter clientePresenter;

    public AtualizarClienteUseCase(ClienteGatewayRepository clienteRepository, ClientePresenter clientePresenter) {
        this.clienteRepository = clienteRepository;
        this.clientePresenter = clientePresenter;
    }


    public ClienteResponseDTO executar(UUID id, AtualizarClienteRequestDTO dto) {
        ClienteDomain clienteAtual = clienteRepository.buscarClientePorId(id);

        boolean loginEmUso = clienteRepository.existsByLogin(dto.login());
        if (loginEmUso && !clienteAtual.getLogin().equals(dto.login())) {
            throw new IllegalArgumentException("Login já está em uso");
        }

        ClienteDomain clienteAtualizado = UsuarioMapper.toClienteDomain(dto, clienteAtual.getSenha());

        clienteRepository.alterarInformacoesDoCliente(clienteAtualizado, clienteAtualizado.getSenha());

        return clientePresenter.retornarCliente(clienteAtualizado);

    }
}
