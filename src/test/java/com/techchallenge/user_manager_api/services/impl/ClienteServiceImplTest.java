package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.config.SecurityFilter;
import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.Endereco;
import com.techchallenge.user_manager_api.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.entities.enums.TiposComidaEnum;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.repositories.ClienteRepository;
import com.techchallenge.user_manager_api.services.PasswordService;
import com.techchallenge.user_manager_api.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Set;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteServiceImpl clienteService;

    private ClienteRequestDTO clienteRequestDTO;

    private EnderecoRequestDTO enderecoRequestDTO;

    @Captor
    private ArgumentCaptor<Cliente> clienteArgumentCaptor;

    @Mock
    Cliente cliente;

    private List<EnderecoRequestDTO> enderecos;

    @Mock
    TokenService tokenService;

    @Mock
    UsuarioService usuarioService;

    @Mock
    private PasswordService passwordService;

    @BeforeEach
    void setup() {
        clienteService = new ClienteServiceImpl(clienteRepository, passwordService,  tokenService,  usuarioService);
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
    void deveriaRetornarErroAoCadastrarClienteComEmailJaExistente() {
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

        when(clienteRepository.save(any())).thenThrow(new DataIntegrityViolationException("Já existe um cadastro com este e-mail."));

        assertThrows(DataIntegrityViolationException.class, () -> {
            clienteService.cadastrarCliente(clienteRequestDTO);
        });
    }

    @Test
    void deveriaRetornarSucessoAoBuscarClientePorId() {
        // ARRANGE
        UUID id = UUID.randomUUID();

        Cliente clienteMock = mock(Cliente.class);
        when(clienteMock.getId()).thenReturn(id);
        when(clienteMock.getEmail()).thenReturn("joao@email.com");
        when(clienteMock.getCpf()).thenReturn("123.456.789-00");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteMock));

        // ACT
        ClienteResponseDTO resultado = clienteService.buscarCliente(id);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("joao@email.com", resultado.email());
        assertEquals("123.456.789-00", resultado.cpf());
        assertEquals(id, resultado.id());
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