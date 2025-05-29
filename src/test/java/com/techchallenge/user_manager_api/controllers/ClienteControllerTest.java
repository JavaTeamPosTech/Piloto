package com.techchallenge.user_manager_api.controllers;

import com.jayway.jsonpath.JsonPath;
import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.dto.response.EnderecoResponseDTO;
import com.techchallenge.user_manager_api.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.entities.enums.TiposComidaEnum;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.repositories.ClienteRepository;
import com.techchallenge.user_manager_api.services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "rafaelLogin123", roles = {"CLIENTE"})
class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setupSecurityContext() {
        UUID uuidCliente = UUID.fromString("11111111-1111-1111-1111-111111111111");

        var principalMock = new Object() {
            public UUID id = uuidCliente;
        };

        var auth = new UsernamePasswordAuthenticationToken(
                principalMock,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void deveriaRetornar200AoCadastrarCliente() throws Exception {
        // ARRANGE
        String json = """
                {
                  "nome": "João da Silva",
                  "cpf": "123.456.789-00",
                  "email": "joaodasilva@email.com",
                  "login": "joaodasilva",
                  "dataNascimento": "1990-01-01",
                  "genero": "MASCULINO",
                  "telefone": "+5581999992345",
                  "preferenciasAlimentares": [
                    "VEGETARIANA",
                    "SAUDAVEL"
                  ],
                  "alergias": [
                    "AMENDOIM",
                    "LACTOSE"
                  ],
                  "metodoPagamentoPreferido": "CREDITO",
                  "clienteVip": true,
                  "notificacoesAtivas": true,
                  "senha": "SenhaForte123!",
                  "enderecos": [
                    {
                      "estado": "SP",
                      "cidade": "Sao Paulo",
                      "bairro": "Jardim Paulista",
                      "rua": "Avenida Paulista",
                      "numero": 123,
                      "complemento": "Apt 101",
                      "cep": "01311-000"
                    }
                  ]
                }
                """;
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO( UUID.randomUUID(),
                "Rafael",
                "rafael1@gmail.com",
                "rafael1Login");
        CadastroResponseDTO cadastroResponseDTO = new CadastroResponseDTO(usuarioResponseDTO,"");


        when(clienteService.cadastrarCliente(any())).thenReturn(cadastroResponseDTO);

        //ACT
        var response = mvc.perform(
                        post("/clientes")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400AoCadastrarClienteTotalmenteVazio() throws Exception {
        //ARRANGE
        String json = "{}";
        //ACT
        var response = mvc.perform(
                post("/clientes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400AoCadastrarClienteSemCpfEEmail() throws Exception {
        // ARRANGE
        // ARRANGE
        String json = """
                {
                  "nome": "João da Silva",
                  "cpf": "",
                  "email": "",
                  "login": "joaodasilva",
                  "dataNascimento": "1990-01-01",
                  "genero": "MASCULINO",
                  "telefone": "+5581999992345",
                  "preferenciasAlimentares": [
                    "VEGETARIANA",
                    "SAUDAVEL"
                  ],
                  "alergias": [
                    "AMENDOIM",
                    "LACTOSE"
                  ],
                  "metodoPagamentoPreferido": "CREDITO",
                  "clienteVip": true,
                  "notificacoesAtivas": true,
                  "senha": "SenhaForte123!",
                  "enderecos": [
                    {
                      "estado": "SP",
                      "cidade": "Sao Paulo",
                      "bairro": "Jardim Paulista",
                      "rua": "Avenida Paulista",
                      "numero": 123,
                      "complemento": "Apt 101",
                      "cep": "01311-000"
                    }
                  ]
                }
                """;
        //ACT
        var response = mvc.perform(
                post("/clientes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200AoBuscarClientePorId() throws Exception {
        UUID uuidCliente = UUID.fromString("11111111-1111-1111-1111-111111111111");

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                uuidCliente,
                "Rafael",
                "311.258.789-01",
                LocalDate.now(),
                "rafael@gmail.com",
                "rafaelLogin123",
                "(11) 91284-5671",
                List.of()
        );

        when(clienteService.buscarCliente(uuidCliente)).thenReturn(clienteResponseDTO);

        mvc.perform(get("/clientes/" + uuidCliente))
                .andExpect(status().isOk());
    }


    @Test
    void deveriaRetornar404AoBuscarProprietarioInexistente() throws Exception {
        UUID uuidCliente = UUID.fromString("11111111-1111-1111-1111-111111111111");

        when(clienteService.buscarCliente(uuidCliente))
                .thenThrow(new ResourceNotFoundException("Cliente não encontrado"));

        var response = mvc.perform(get("/clientes/" + uuidCliente))
                .andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornar409AoCadastrarClienteComCpfExistente() throws Exception {
        //ARRANGE
        String json = """
                {
                    "nome": "João da Silva",
                    "cpf": "123.456.789-00",
                    "email": "joaodasilva@email.com",
                    "login": "joaodasilva",
                    "dataNascimento": "1990-01-01",
                    "genero": "MASCULINO",
                    "telefone": "+5581999992345",
                    "preferenciasAlimentares": [
                      "VEGETARIANA",
                      "SAUDAVEL"
                    ],
                    "alergias": [
                      "AMENDOIM",
                      "LACTOSE"
                    ],
                    "metodoPagamentoPreferido": "CREDITO",
                    "clienteVip": true,
                    "notificacoesAtivas": true,
                    "senha": "SenhaForte123!",
                    "enderecos": [
                      {
                        "estado": "SP",
                        "cidade": "Sao Paulo",
                        "bairro": "Jardim Paulista",
                        "rua": "Avenida Paulista",
                        "numero": 123,
                        "complemento": "Apt 101",
                        "cep": "01311-000"
                      }
                    ]
                  }
                """;

        //ACT
        doThrow(new DataIntegrityViolationException("Já existe um cadastro com este CPF."))
                .when(clienteService)
                .cadastrarCliente(any());

        var response = mvc.perform(
                post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(409, response.getStatus());
    }

    @Test
    void deveriaRetornar409AoCadastrarClienteComEmailExistente() throws Exception {
        //ARRANGE
        String json = """
                {
                    "nome": "João da Silva",
                    "cpf": "123.456.789-00",
                    "email": "joaodasilva@email.com",
                    "login": "joaodasilva",
                    "dataNascimento": "1990-01-01",
                    "genero": "MASCULINO",
                    "telefone": "+5581999992345",
                    "preferenciasAlimentares": [
                      "VEGETARIANA",
                      "SAUDAVEL"
                    ],
                    "alergias": [
                      "AMENDOIM",
                      "LACTOSE"
                    ],
                    "metodoPagamentoPreferido": "CREDITO",
                    "clienteVip": true,
                    "notificacoesAtivas": true,
                    "senha": "SenhaForte123!",
                    "enderecos": [
                      {
                        "estado": "SP",
                        "cidade": "Sao Paulo",
                        "bairro": "Jardim Paulista",
                        "rua": "Avenida Paulista",
                        "numero": 123,
                        "complemento": "Apt 101",
                        "cep": "01311-000"
                      }
                    ]
                  }
                """;

        //ACT
        doThrow(new DataIntegrityViolationException("Já existe um cadastro com este email."))
                .when(clienteService)
                .cadastrarCliente(any());

        var response = mvc.perform(
                post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(409, response.getStatus());
    }
}