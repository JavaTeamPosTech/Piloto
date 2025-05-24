package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.mapper.UsuarioMapper;
import com.techchallenge.user_manager_api.repositories.ClienteRepository;
import com.techchallenge.user_manager_api.services.ClienteService;
import com.techchallenge.user_manager_api.services.PasswordService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordService  passwordService;

    public ClienteServiceImpl(ClienteRepository clienteRepository, PasswordService passwordService) {
        this.clienteRepository = clienteRepository;
        this.passwordService = passwordService;
    }


    @Override
    public void cadastrarCliente(ClienteRequestDTO clienteDTO) {
        String senhaCriptografada = passwordService.encryptPassword(clienteDTO.senha());
        Cliente cliente = UsuarioMapper.toCliente(clienteDTO, senhaCriptografada);
        clienteRepository.save(cliente);
    }

    @Override
    public ClienteResponseDTO buscarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return UsuarioMapper.toClienteResponseDTO(cliente);
    }

}
