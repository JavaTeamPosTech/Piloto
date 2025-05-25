package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClienteServiceImpl clienteService;

    @Test
    void deveriaRetornar200AoCadastrarCliente() throws Exception {
        //ARRANGE

        // ARRANGE
        String json = """
                {
                  "cpf": "12345678900",
                  "dataNascimento": "1990-05-24",
                  "genero": "MASCULINO",
                  "telefone": "(11) 91234-5678",
                  "preferenciasAlimentares": ["CHURRASCO", "JAPONESA"],
                  "alergias": ["GLUTEN"],
                  "metodoPagamentoPreferido": "PIX",
                  "clienteVip": true,
                  "notificacoesAtivas": true,
                  "nome": "João da Silva",
                  "email": "joao@email.com",
                  "login": "joaosilva",
                  "senha": "senhaSegura123",
                  "enderecos": [
                    {
                      "estado": "SP",
                      "cidade": "São Paulo",
                      "bairro": "Centro",
                      "rua": "Rua das Rosas",
                      "numero": 123,
                      "complemento": "Apto 42",
                      "cep": "01000-000"
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
        String json = """
                {
                  "cpf": "",
                  "dataNascimento": "1990-05-24",
                  "genero": "MASCULINO",
                  "telefone": "(11) 91234-5678",
                  "preferenciasAlimentares": ["CHURRASCO", "JAPONESA"],
                  "alergias": ["GLUTEN"],
                  "metodoPagamentoPreferido": "PIX",
                  "clienteVip": true,
                  "notificacoesAtivas": true,
                  "nome": "João da Silva",
                  "email": "",
                  "login": "joaosilva",
                  "senha": "senhaSegura123",
                  "enderecos": [
                    {
                      "estado": "SP",
                      "cidade": "São Paulo",
                      "bairro": "Centro",
                      "rua": "Rua das Rosas",
                      "numero": 123,
                      "complemento": "Apto 42",
                      "cep": "01000-000"
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
        //ARRANGE
        Long id = 1L;

        //ACT
        var response = mvc.perform(
                get("/clientes/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar404AoBuscarClientePorId() throws Exception {
        //ARRANGE
        Long id = 1L;

        //ACT
        when(clienteService.buscarCliente(id)).thenThrow(new ResourceNotFoundException("Cliente não encontrado"));

        var response = mvc.perform(
                get("/clientes/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornar409AoCadastrarClienteComCpfExistente() throws Exception {

        //ARRANGE
        String json = """
                {
                  "cpf": "12345678900",
                  "dataNascimento": "1990-05-24",
                  "genero": "MASCULINO",
                  "telefone": "(11) 91234-5678",
                  "preferenciasAlimentares": ["CHURRASCO", "JAPONESA"],
                  "alergias": ["GLUTEN"],
                  "metodoPagamentoPreferido": "PIX",
                  "clienteVip": true,
                  "notificacoesAtivas": true,
                  "nome": "João da Silva",
                  "email": "joao@email.com",
                  "login": "joaosilva",
                  "senha": "senhaSegura123",
                  "enderecos": [
                    {
                      "estado": "SP",
                      "cidade": "São Paulo",
                      "bairro": "Centro",
                      "rua": "Rua das Rosas",
                      "numero": 123,
                      "complemento": "Apto 42",
                      "cep": "01000-000"
                    }
                  ]
                }
                """;

        //ACT
        doThrow(new DataIntegrityViolationException("Já existe um cadastro com este e-mail."))
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