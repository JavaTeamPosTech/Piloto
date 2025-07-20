package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.application.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.infra.model.ClienteEntity;
import com.techchallenge.user_manager_api.infra.persistence.adapters.UsuarioAdapter;
import com.techchallenge.user_manager_api.infra.repositories.ClienteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return UsuarioAdapter.toClienteDomain(verificarClienteExiste(id));
    }

    @Override
    public ClienteDomain alterarInformacoesDoCliente(ClienteDomain domain, String senhaCriptografada){
        ClienteEntity clienteEntity = UsuarioAdapter.toCliente(domain, senhaCriptografada);
        clienteRepository.save(clienteEntity);
        return UsuarioAdapter.toClienteDomain(clienteEntity);
    }


    @Override
    public List<ClienteDomain> buscarTodosClientes() {
        Page<ClienteEntity> clientes = clienteRepository.findAll(PageRequest.of(0, 10));
        return clientes.stream()
                .map(UsuarioAdapter::toClienteDomain)
                .toList();
    }


    private ClienteEntity verificarClienteExiste(UUID id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com o id '%s' não encontrado: ", id)));
    }

    @Override
    public void deletarClientePorId(UUID id){
        verificarClienteExiste(id);
        clienteRepository.deleteById(id);
        if (clienteRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Falha ao deletar cliente");
        }

    }
}
