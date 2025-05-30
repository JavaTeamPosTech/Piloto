package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.AtualizarClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.Endereco;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.mapper.UsuarioMapper;
import com.techchallenge.user_manager_api.repositories.ClienteRepository;
import com.techchallenge.user_manager_api.services.ClienteService;
import com.techchallenge.user_manager_api.services.PasswordService;
import com.techchallenge.user_manager_api.services.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public ClienteServiceImpl(ClienteRepository clienteRepository, PasswordService passwordService, TokenService tokenService, UsuarioService usuarioService) {
        this.clienteRepository = clienteRepository;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    public CadastroResponseDTO cadastrarCliente(ClienteRequestDTO clienteDTO) {

        if (usuarioService.existsByLogin(clienteDTO.login())) {
            throw new DataIntegrityViolationException("uk_usuario_login:  O login '" + clienteDTO.login() + "' já está em uso.");
        }

        String senhaCriptografada = passwordService.encryptPassword(clienteDTO.senha());
        Cliente cliente = UsuarioMapper.toCliente(clienteDTO, senhaCriptografada);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        String token = tokenService.generateToken(clienteSalvo.getLogin());

        return new CadastroResponseDTO(UsuarioResponseDTO.deCliente(clienteSalvo), token);
    }

    @Override
    public ClienteResponseDTO buscarCliente(UUID id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id '%s' não encontrado", id)));
        return UsuarioMapper.toClienteResponseDTO(cliente);
    }

    @Override
    public List<ClienteResponseDTO> buscarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(UsuarioMapper::toClienteResponseDTO)
                .toList();
    }

    @Override
    public ClienteResponseDTO editarCliente(UUID id, AtualizarClienteRequestDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id '%s' não encontrado", id)));

        if (usuarioService.existsByLogin(clienteDTO.login()) && !cliente.getLogin().equals(clienteDTO.login())) {
            throw new DataIntegrityViolationException("uk_usuario_login: O login '" + clienteDTO.login() + "' já está em uso.");
        }
        atualizarDadosCliente(cliente, clienteDTO);

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return UsuarioMapper.toClienteResponseDTO(clienteRepository.save(clienteAtualizado));

    }

    private void atualizarDadosCliente(Cliente cliente, AtualizarClienteRequestDTO clienteDTO) {
        cliente.setNome(clienteDTO.nome());
        cliente.setCpf(clienteDTO.cpf());
        cliente.setDataNascimento(clienteDTO.dataNascimento());
        cliente.setEmail(clienteDTO.email());
        cliente.setLogin(clienteDTO.login());
        cliente.setTelefone(clienteDTO.telefone());
        cliente.setGenero(clienteDTO.genero());
        cliente.setAlergias(new HashSet<>(clienteDTO.alergias()));
        cliente.setMetodoPagamentoPreferido(clienteDTO.metodoPagamentoPreferido());
        cliente.setPreferenciasAlimentares(new HashSet<>(clienteDTO.preferenciasAlimentares()));
        cliente.setNotificacoesAtivas(clienteDTO.notificacoesAtivas());

        if (clienteDTO.enderecos() != null && !clienteDTO.enderecos().isEmpty()) {
            cliente.getEnderecos().clear();

            clienteDTO.enderecos().forEach(enderecoDTO -> {
                Endereco endereco = new Endereco(enderecoDTO, cliente);
                cliente.getEnderecos().add(endereco);
            });
        }
    }
}
