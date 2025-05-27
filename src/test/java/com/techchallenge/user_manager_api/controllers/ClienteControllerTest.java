package com.techchallenge.user_manager_api.controllers;

import com.jayway.jsonpath.JsonPath;
import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.dto.response.EnderecoResponseDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.entities.enums.TiposComidaEnum;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.repositories.ClienteRepository;
import com.techchallenge.user_manager_api.services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClienteServiceImpl clienteService;

    @Test
    void deveriaRetornar201AoCadastrarCliente() throws Exception {
        // ARRANGE
        String json = """
                {
                   "cpf": "311.258.789-01",
                   "dataNascimento": "1990-05-20",
                   "genero": "MASCULINO",
                   "telefone": "(11) 91284-5671",
                   "preferenciasAlimentares": [
                     "ITALIANA",
                     "DOCES",
                     "JAPONESA"
                   ],
                   "alergias": [
                     "GLUTEN",
                     "PEIXE"
                   ],
                   "metodoPagamentoPreferido": "PIX",
                   "clienteVip": true,
                   "notificacoesAtivas": true,
                   "nome": "Rafael",
                   "email": "rafael1@gmail.com",
                   "login": "rafael1Login",
                   "senha": "senhaSegura123",
                   "enderecos": [
                     {
                       "estado": "SP",
                       "cidade": "São Paulo",
                       "bairro": "Centro",
                       "rua": "Rua das Flores",
                       "numero": 123,
                       "complemento": "Apto 45",
                       "cep": "01000-000"
                     },
                     {
                       "estado": "SP",
                       "cidade": "São Paulo",
                       "bairro": "Vila Mariana",
                       "rua": "Rua São Paulo",
                       "numero": 129,
                       "complemento": "Apto 35",
                       "cep": "09888-010"
                     }
                   ]
                 }
                """;

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                3L,
                "Rafael",
                "311.258.789-01",
               new Date(),
                "rafael1@gmail.com",
                "rafael1Login",
                "(11) 91284-5671",
                List.of(
                        new EnderecoResponseDTO(
                                3L,
                                "SP",
                                "São Paulo",
                                "Centro",
                                "Rua das Flores",
                                123,
                                "Apto 45",
                                "01000-000"
                        ),
                        new EnderecoResponseDTO(
                                4L,
                                "SP",
                                "São Paulo",
                                "Vila Mariana",
                                "Rua São Paulo",
                                129,
                                "Apto 35",
                                "09888-010"
                        )
                )
        );

        when(clienteService.cadastrarCliente(any())).thenReturn(clienteResponseDTO);

        //ACT
        var response = mvc.perform(
                post("/clientes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(201, response.getStatus());
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
                   "cpf": "",
                   "dataNascimento": "1990-05-20",
                   "genero": "MASCULINO",
                   "telefone": "(11) 91284-5671",
                   "preferenciasAlimentares": [
                     "ITALIANA",
                     "DOCES",
                     "JAPONESA"
                   ],
                   "alergias": [
                     "GLUTEN",
                     "PEIXE"
                   ],
                   "metodoPagamentoPreferido": "PIX",
                   "clienteVip": true,
                   "notificacoesAtivas": true,
                   "nome": "Rafael",
                   "email": "",
                   "login": "rafael1Login",
                   "senha": "senhaSegura123",
                   "enderecos": [
                     {
                       "estado": "SP",
                       "cidade": "São Paulo",
                       "bairro": "Centro",
                       "rua": "Rua das Flores",
                       "numero": 123,
                       "complemento": "Apto 45",
                       "cep": "01000-000"
                     },
                     {
                       "estado": "SP",
                       "cidade": "São Paulo",
                       "bairro": "Vila Mariana",
                       "rua": "Rua São Paulo",
                       "numero": 129,
                       "complemento": "Apto 35",
                       "cep": "09888-010"
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

    @WithMockUser(username = "rafaelLogin123", roles = {"CLIENTE"})
    @Test
    void deveriaRetornar200AoBuscarClientePorIdMockandoAutenticacao() throws Exception {
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                3L,
                "Rafael",
                "311.258.789-01",
                new Date(),
                "rafael@gmail.com",
                "rafaelLogin123",
                "(11) 91284-5671",
                List.of(
                        new EnderecoResponseDTO(
                                3L,
                                "SP",
                                "São Paulo",
                                "Centro",
                                "Rua das Flores",
                                123,
                                "Apto 45",
                                "01000-000"
                        ),
                        new EnderecoResponseDTO(
                                4L,
                                "SP",
                                "São Paulo",
                                "Vila Mariana",
                                "Rua São Paulo",
                                129,
                                "Apto 35",
                                "09888-010"
                        )
                )
        );

        when(clienteService.buscarCliente(3L)).thenReturn(clienteResponseDTO);

        mvc.perform(get("/clientes/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.login").value("rafaelLogin123"))
                .andExpect(jsonPath("$.email").value("rafael@gmail.com"));
    }

    @WithMockUser(username = "rafaelLogin123", roles = {"CLIENTE"})
    @Test
    void deveriaRetornar404AoBuscarClientePorIdMockandoAutenticacao() throws Exception {
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                3L,
                "Rafael",
                "311.258.789-01",
                new Date(),
                "rafael@gmail.com",
                "rafaelLogin123",
                "(11) 91284-5671",
                List.of(
                        new EnderecoResponseDTO(
                                3L,
                                "SP",
                                "São Paulo",
                                "Centro",
                                "Rua das Flores",
                                123,
                                "Apto 45",
                                "01000-000"
                        ),
                        new EnderecoResponseDTO(
                                4L,
                                "SP",
                                "São Paulo",
                                "Vila Mariana",
                                "Rua São Paulo",
                                129,
                                "Apto 35",
                                "09888-010"
                        )
                )
        );

        when(clienteService.buscarCliente(3L)).thenThrow(new ResourceNotFoundException("Cliente não encontrado"));

        mvc.perform(get("/clientes/3"))
                .andReturn().getResponse();

        assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarCliente(3L));
    }

    @Test
    void deveriaRetornar409AoCadastrarClienteComCpfExistente() throws Exception {
        //ARRANGE
        String json = """
                {
                   "cpf": "311.258.789-01",
                   "dataNascimento": "1990-05-20",
                   "genero": "MASCULINO",
                   "telefone": "(11) 91284-5671",
                   "preferenciasAlimentares": [
                     "ITALIANA",
                     "DOCES",
                     "JAPONESA"
                   ],
                   "alergias": [
                     "GLUTEN",
                     "PEIXE"
                   ],
                   "metodoPagamentoPreferido": "PIX",
                   "clienteVip": true,
                   "notificacoesAtivas": true,
                   "nome": "Rafael",
                   "email": "rafael1@gmail.com",
                   "login": "rafael1Login",
                   "senha": "senhaSegura123",
                   "enderecos": [
                     {
                       "estado": "SP",
                       "cidade": "São Paulo",
                       "bairro": "Centro",
                       "rua": "Rua das Flores",
                       "numero": 123,
                       "complemento": "Apto 45",
                       "cep": "01000-000"
                     },
                     {
                       "estado": "SP",
                       "cidade": "São Paulo",
                       "bairro": "Vila Mariana",
                       "rua": "Rua São Paulo",
                       "numero": 129,
                       "complemento": "Apto 35",
                       "cep": "09888-010"
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
                   "cpf": "311.258.789-01",
                   "dataNascimento": "1990-05-20",
                   "genero": "MASCULINO",
                   "telefone": "(11) 91284-5671",
                   "preferenciasAlimentares": [
                     "ITALIANA",
                     "DOCES",
                     "JAPONESA"
                   ],
                   "alergias": [
                     "GLUTEN",
                     "PEIXE"
                   ],
                   "metodoPagamentoPreferido": "PIX",
                   "clienteVip": true,
                   "notificacoesAtivas": true,
                   "nome": "Rafael",
                   "email": "rafael1@gmail.com",
                   "login": "rafael1Login",
                   "senha": "senhaSegura123",
                   "enderecos": [
                     {
                       "estado": "SP",
                       "cidade": "São Paulo",
                       "bairro": "Centro",
                       "rua": "Rua das Flores",
                       "numero": 123,
                       "complemento": "Apto 45",
                       "cep": "01000-000"
                     },
                     {
                       "estado": "SP",
                       "cidade": "São Paulo",
                       "bairro": "Vila Mariana",
                       "rua": "Rua São Paulo",
                       "numero": 129,
                       "complemento": "Apto 35",
                       "cep": "09888-010"
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