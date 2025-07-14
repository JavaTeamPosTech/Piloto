package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.naousar.dto.requests.AtualizarClienteRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.naousar.entities.Cliente;
import com.techchallenge.user_manager_api.naousar.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.naousar.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.naousar.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.naousar.entities.enums.TiposComidaEnum;
import com.techchallenge.user_manager_api.naousar.repositories.ClienteRepository;
import com.techchallenge.user_manager_api.naousar.services.PasswordService;
import com.techchallenge.user_manager_api.naousar.services.UsuarioService;
import com.techchallenge.user_manager_api.naousar.services.impl.ClienteServiceImpl;
import com.techchallenge.user_manager_api.naousar.services.impl.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private ClienteRequestDTO clienteRequestDTO;

    @Captor
    private ArgumentCaptor<Cliente> clienteArgumentCaptor;

    @BeforeEach
    void setUp() {
        clienteRequestDTO = new ClienteRequestDTO(
                "João da Silva",
                "123.456.789-00",
                "joaodasilva@email.com",
                "joaodasilva",
                LocalDate.parse("1990-01-01"),
                GeneroEnum.MASCULINO,
                "+5581999992345",
                Set.of(TiposComidaEnum.VEGETARIANA, TiposComidaEnum.SAUDAVEL),
                Set.of(AlergiaAlimentarEnum.AMENDOIM, AlergiaAlimentarEnum.LACTOSE),
                MetodoPagamentoEnum.CREDITO,
                true,
                true,
                "SenhaForte123!",
                List.of(
                        new EnderecoRequestDTO(
                                "SP",
                                "Sao Paulo",
                                "Jardim Paulista",
                                "Avenida Paulista",
                                123,
                                "Apt 101",
                                "01311-000"
                        )
                )
        );
    }

    @Test
    void cadastrarCliente_CadastroBemSucedido() {
        Cliente cliente = new Cliente();
        cliente.setLogin("login");
        when(usuarioService.existsByLogin(clienteRequestDTO.login())).thenReturn(false);
        when(passwordService.encryptPassword(clienteRequestDTO.senha())).thenReturn("senhaCriptografada");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(tokenService.generateToken(cliente.getLogin())).thenReturn("token");

        CadastroResponseDTO response = clienteService.cadastrarCliente(clienteRequestDTO);

        assertNotNull(response);
        assertEquals("login", response.usuario().login());
        assertEquals("token", response.token());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void cadastrarCliente_LoginJaEmUso() {
        when(usuarioService.existsByLogin(clienteRequestDTO.login())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> clienteService.cadastrarCliente(clienteRequestDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void buscarClientes_RetornaListaDeClientes() {
        Cliente cliente1 = new Cliente();
        cliente1.setLogin("login1");
        Cliente cliente2 = new Cliente();
        cliente2.setLogin("login2");

        when(clienteRepository.findAll()).thenReturn(List.of(cliente1, cliente2));

        List<ClienteResponseDTO> response = clienteService.buscarClientes();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("login1", response.get(0).login());
        assertEquals("login2", response.get(1).login());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void buscarCliente_ClienteEncontrado() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente();
        cliente.setLogin("login");
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        ClienteResponseDTO response = clienteService.buscarCliente(id);

        assertNotNull(response);
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    void buscarCliente_ClienteNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarCliente(id));
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    void editarCliente_AtualizacaoBemSucedida() {
        UUID id = UUID.randomUUID();

        AtualizarClienteRequestDTO atualizarClienteRequestDTO = new AtualizarClienteRequestDTO(
                "João da Silva",
                "123.456.789-00",
                "joaodasilva@email.com",
                "novoLogin",
                LocalDate.parse("1990-01-01"),
                GeneroEnum.MASCULINO,
                "+5581999992345",
                Set.of(TiposComidaEnum.VEGETARIANA, TiposComidaEnum.SAUDAVEL),
                Set.of(AlergiaAlimentarEnum.AMENDOIM, AlergiaAlimentarEnum.LACTOSE),
                MetodoPagamentoEnum.CREDITO,
                true,
                List.of(
                        new EnderecoRequestDTO(
                                "SP",
                                "Sao Paulo",
                                "Jardim Paulista",
                                "Avenida Paulista",
                                123,
                                "Apt 101",
                                "01311-000"
                        )
                )
        );

        Cliente cliente = new Cliente();
        cliente.setLogin("joaodasilva");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(usuarioService.existsByLogin(atualizarClienteRequestDTO.login())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDTO response = clienteService.editarCliente(id, atualizarClienteRequestDTO);

        assertNotNull(response);
        assertEquals("novoLogin", response.login());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void editarCliente_LoginJaEmUso() {
        UUID id = UUID.randomUUID();
        AtualizarClienteRequestDTO atualizarClienteRequestDTO = new AtualizarClienteRequestDTO(
                "novo nome",
                "123.456.789-00",
                "joaodasilva@email.com",
                "novologin",
                LocalDate.parse("1990-01-01"),
                GeneroEnum.MASCULINO,
                "+5581999992345",
                Set.of(TiposComidaEnum.VEGETARIANA, TiposComidaEnum.SAUDAVEL),
                Set.of(AlergiaAlimentarEnum.AMENDOIM, AlergiaAlimentarEnum.LACTOSE),
                MetodoPagamentoEnum.CREDITO,
                true,
                List.of(
                        new EnderecoRequestDTO(
                                "SP",
                                "Sao Paulo",
                                "Jardim Paulista",
                                "Avenida Paulista",
                                123,
                                "Apt 101",
                                "01311-000"
                        )
                )
        );
        Cliente cliente = new Cliente();
        cliente.setLogin("joaodasilva");
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(usuarioService.existsByLogin(atualizarClienteRequestDTO.login())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> clienteService.editarCliente(id, atualizarClienteRequestDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deletarCliente_ClienteRemovido() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.existsById(id)).thenReturn(false);

        clienteService.deletarCliente(id);

        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    void deletarCliente_ClienteNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.deletarCliente(id));
        verify(clienteRepository, never()).delete(any(Cliente.class));
    }

    //Adicoes teste
    @Test
    void deveRetornarErroCadastrarComCpfJaExistente() {
        this.clienteRequestDTO = new ClienteRequestDTO(
                "João da Silva",
                "123.456.789-00",
                "joaodasilva@email.com",
                "joaodasilva",
                LocalDate.now(),
                GeneroEnum.MASCULINO,
                "+5581999992345",
                Set.of(TiposComidaEnum.CHURRASCO, TiposComidaEnum.JAPONESA),
                Set.of(AlergiaAlimentarEnum.GLUTEN),
                MetodoPagamentoEnum.PIX,
                true,
                true,
                "SenhaForte123!",
                List.of(new EnderecoRequestDTO(
                        "SP", "São Paulo", "Centro", "Rua das Rosas", 123, "Apto 42", "01000-000"
                ))
        );

        when(clienteRepository.save(any())).thenThrow(new DataIntegrityViolationException("Já existe um cadastro com este CPF."));

        assertThrows(DataIntegrityViolationException.class, () -> {
            clienteService.cadastrarCliente(clienteRequestDTO);
        });
    }

    @Test
    void deveRetornarSucessoCadastrarCliente() {
        this.clienteRequestDTO = new ClienteRequestDTO(
                "João da Silva",
                "123.456.789-00",
                "joaodasilva@email.com",
                "joaodasilva",
                LocalDate.now(),
                GeneroEnum.MASCULINO,
                "+5581999992345",
                Set.of(TiposComidaEnum.CHURRASCO, TiposComidaEnum.JAPONESA),
                Set.of(AlergiaAlimentarEnum.GLUTEN),
                MetodoPagamentoEnum.PIX,
                true,
                true,
                "SenhaForte123!",
                List.of(new EnderecoRequestDTO(
                        "SP", "São Paulo", "Centro", "Rua das Rosas", 123, "Apto 42", "01000-000"
                ))
        );

        when(clienteRepository.save(any(Cliente.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        clienteService.cadastrarCliente(clienteRequestDTO);

        then(clienteRepository).should().save(clienteArgumentCaptor.capture());
        Cliente clienteSalvo = clienteArgumentCaptor.getValue();

        assertEquals("123.456.789-00", clienteSalvo.getCpf());
        assertEquals("joaodasilva@email.com", clienteSalvo.getEmail());
    }

    @Test
    void deveriaRetornarErroAoBuscarClientePorId() throws Exception {
        // ARRANGE
        UUID id = UUID.randomUUID();

        //ACT
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        //ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.buscarCliente(id);
        });
    }

}