package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.application.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.infra.model.ClienteEntity;
import com.techchallenge.user_manager_api.infra.persistence.adapters.UsuarioAdapter;
import com.techchallenge.user_manager_api.infra.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteModelRepository implements ClienteGatewayRepository {

    private final ClienteRepository clienteRepository;

    public ClienteModelRepository(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDomain cadastrarCliente(ClienteDomain clienteDomain, String senhaCriptografada) {
        ClienteEntity clienteEntity = UsuarioAdapter.toCliente(clienteDomain, senhaCriptografada);
        clienteRepository.save(clienteEntity);
        return UsuarioAdapter.toClienteDomain(clienteEntity);
    }

    @Override
    public boolean existsByLogin(String login) {
        return false;
    }

    @Override
    public ClienteDomain buscarClientePorId(UUID id) {
        //TODO verificar se podemos lançar exceção no repository
        ClienteEntity cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com o id '%s' não encontrado: ", id)));

        return UsuarioAdapter.toClienteDomain(cliente);
    }

    @Override
    public ClienteDomain alterarInformacoesDoCliente(ClienteDomain domain, String senhaCriptografada){
        ClienteEntity clienteEntity = UsuarioAdapter.toCliente(domain, senhaCriptografada);
        clienteRepository.save(clienteEntity);
        return UsuarioAdapter.toClienteDomain(clienteEntity);
    }
}
