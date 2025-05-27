package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.entities.enums.TiposComidaEnum;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.repositories.ClienteRepository;
import com.techchallenge.user_manager_api.services.PasswordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    private PasswordService passwordService;


    @BeforeEach
    void setUp() {
        clienteService = new ClienteServiceImpl(clienteRepository,passwordService);
    }

    @Test
    void deveriaRetornarSucessoAoCadastrarCliente() {
        // ARRANGE
        this.enderecoRequestDTO = new EnderecoRequestDTO(
                "São Paulo", "São Paulo", "Centro", "Rua Exemplo", 123, "Apto 301", "01000-000");
        this.enderecos = List.of(enderecoRequestDTO);
        this.clienteRequestDTO = new ClienteRequestDTO(
                "123.456.789-00",
                new Date(),
                GeneroEnum.MASCULINO,
                "(11) 91234-5678",
                Set.of(TiposComidaEnum.CHURRASCO, TiposComidaEnum.JAPONESA),
                Set.of(AlergiaAlimentarEnum.GLUTEN),
                MetodoPagamentoEnum.PIX,
                true,
                true,
                "João da Silva",
                "joao@email.com",
                "joaosilva",
                "senhaSegura123",
                List.of(
                        new EnderecoRequestDTO(
                                "SP",
                                "São Paulo",
                                "Centro",
                                "Rua das Rosas",
                                123,
                                "Apto 42",
                                "01000-000"
                        )
                )
        );

        // ACT
        //clienteService.cadastrarCliente(clienteRequestDTO);

        // ASSERT
        then(clienteRepository).should().save(clienteArgumentCaptor.capture());
        Cliente clienteSalvo = clienteArgumentCaptor.getValue();

        assertEquals("123.456.789-00", clienteSalvo.getCpf());
        assertEquals("joao@email.com", clienteSalvo.getEmail());
    }


    @Test
    void deveriaRetornarErroAoCadastrarClienteComCpfJaExistente() throws Exception {
        // ARRANGE
        this.enderecoRequestDTO = new EnderecoRequestDTO(
                "São Paulo", "São Paulo", "Centro", "Rua Exemplo", 123, "Apto 301", "01000-000");
        this.enderecos = List.of(enderecoRequestDTO);
        this.clienteRequestDTO = new ClienteRequestDTO(
                "123.456.789-00",
                new Date(),
                GeneroEnum.MASCULINO,
                "(11) 91234-5678",
                Set.of(TiposComidaEnum.CHURRASCO, TiposComidaEnum.JAPONESA),
                Set.of(AlergiaAlimentarEnum.GLUTEN),
                MetodoPagamentoEnum.PIX,
                true,
                true,
                "João da Silva",
                "joao@email.com",
                "joaosilva",
                "senhaSegura123",
                List.of(
                        new EnderecoRequestDTO(
                                "SP",
                                "São Paulo",
                                "Centro",
                                "Rua das Rosas",
                                123,
                                "Apto 42",
                                "01000-000"
                        )
                )
        );

        when(clienteRepository.save(any())).thenThrow(new DataIntegrityViolationException("Já existe um cadastro com este CPF."));

//        assertThrows(DataIntegrityViolationException.class, () -> {
//            clienteService.cadastrarCliente(clienteRequestDTO);
//        });
    }

    @Test
    void deveriaRetornarErroAoCadastrarClienteComEmailJaExistente() throws Exception {
        // ARRANGE
        this.enderecoRequestDTO = new EnderecoRequestDTO(
                "São Paulo", "São Paulo", "Centro", "Rua Exemplo", 123, "Apto 301", "01000-000");
        this.enderecos = List.of(enderecoRequestDTO);
        this.clienteRequestDTO = new ClienteRequestDTO(
                "123.456.789-00",
                new Date(),
                GeneroEnum.MASCULINO,
                "(11) 91234-5678",
                Set.of(TiposComidaEnum.CHURRASCO, TiposComidaEnum.JAPONESA),
                Set.of(AlergiaAlimentarEnum.GLUTEN),
                MetodoPagamentoEnum.PIX,
                true,
                true,
                "João da Silva",
                "joao@email.com",
                "joaosilva",
                "senhaSegura123",
                List.of(
                        new EnderecoRequestDTO(
                                "SP",
                                "São Paulo",
                                "Centro",
                                "Rua das Rosas",
                                123,
                                "Apto 42",
                                "01000-000"
                        )
                )
        );

        when(clienteRepository.save(any())).thenThrow(new DataIntegrityViolationException("Já existe um cadastro com este e-mail."));

//        assertThrows(DataIntegrityViolationException.class, () -> {
//            clienteService.cadastrarCliente(clienteRequestDTO);
//        });
    }

    @Test
    void deveriaRetornarSucessoAoBuscarClientePorId() {
        // ARRANGE
        Long id = 1L;

        Cliente clienteMock = mock(Cliente.class);
        when(clienteMock.getId()).thenReturn(id);
        when(clienteMock.getEmail()).thenReturn("joao@email.com");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteMock));

        // ACT
        ClienteResponseDTO resultado = clienteService.buscarCliente(id);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("joao@email.com", resultado.email());
        assertEquals(1L, resultado.id());
    }

    @Test
    void deveriaRetornarErroAoBuscarClientePorId() throws Exception {
        // ARRANGE
        Long id = 1L;

        //ACT
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        //ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.buscarCliente(id);
        });
    }

}