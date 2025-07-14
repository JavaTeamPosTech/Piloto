package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.infra.model.ClienteEntity;
import com.techchallenge.user_manager_api.infra.persistence.adapters.UsuarioAdapter;
import com.techchallenge.user_manager_api.infra.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteModelRepository implements ClienteGatewayRepository {

    private final ClienteRepository clienteRepository;

    public  ClienteModelRepository(ClienteRepository clienteRepository) {
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
}
