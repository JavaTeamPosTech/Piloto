package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.ClienteRequestDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.exceptions.EmailAlreadyInUseException;
import com.techchallenge.user_manager_api.mapper.UsuarioMapper;
import com.techchallenge.user_manager_api.repositories.ClienteRepository;
import com.techchallenge.user_manager_api.services.ClienteService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @Override
    public void cadastrarCliente(ClienteRequestDTO clienteDTO) {
        if(existeClientePorEmail(clienteDTO.email())){
            throw new EmailAlreadyInUseException(clienteDTO.email());
        }
        Cliente cliente = UsuarioMapper.toCliente(clienteDTO);
        clienteRepository.save(cliente);
    }

    @Override
    public Boolean existeClientePorEmail(String email) {
        return clienteRepository.existsClienteByEmail(email);
    }
}
