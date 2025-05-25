package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.services.impl.ProprietarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class ProprietarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProprietarioServiceImpl proprietarioService;

    @Test
    void deveriaRetornar200AoCadastrarProprietario() throws Exception {
        //ARRANGE
        String json = """
                {
                  "cnpj": "12.345.678/0001-90",
                  "razaoSocial": "Empresa Exemplo LTDA",
                  "nomeFantasia": "Empresa Exemplo",
                  "inscricaoEstadual": "123456789",
                  "telefoneComercial": "(11) 1234-5678",
                  "whatsapp": "(11) 91234-5678",
                  "statusConta": "ATIVO",
                  "nome": "Carlos Silva",
                  "email": "carlos.silva@email.com",
                  "login": "carloss",
                  "senha": "senhaForte123",
                  "enderecos": [
                    {
                      "estado": "SP",
                      "cidade": "São Paulo",
                      "bairro": "Centro",
                      "rua": "Rua das Flores",
                      "numero": 100,
                      "complemento": "Sala 10",
                      "cep": "01000-000"
                    }
                  ]
                }
                
                """;

        //ACT
        var resultado = mvc.perform(
                post("/proprietario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, resultado.getStatus());
    }

    @Test
    void deveriaRetornar400AoCadastrarProprietarioVazio() throws Exception {
        //ARRANGE
        String json = """
                """;

        //ACT
        var resultado = mvc.perform(
                post("/proprietario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, resultado.getStatus());
    }

    @Test
    void deveriaRetornar400AoCadastrarProprietarioSemCnpjEEmail() throws Exception {
        //ARRANGE
        String json = """
                {
                  "cnpj": "",
                  "razaoSocial": "Empresa Exemplo LTDA",
                  "nomeFantasia": "Empresa Exemplo",
                  "inscricaoEstadual": "123456789",
                  "telefoneComercial": "(11) 1234-5678",
                  "whatsapp": "(11) 91234-5678",
                  "statusConta": "ATIVO",
                  "nome": "Carlos Silva",
                  "email": "",
                  "login": "carloss",
                  "senha": "senhaForte123",
                  "enderecos": [
                    {
                      "estado": "SP",
                      "cidade": "São Paulo",
                      "bairro": "Centro",
                      "rua": "Rua das Flores",
                      "numero": 100,
                      "complemento": "Sala 10",
                      "cep": "01000-000"
                    }
                  ]
                }
                """;

        var resultado = mvc.perform(
                post("/proprietario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400,resultado.getStatus());
    }

    @Test
    void deveriaRetornar200AoBuscarProprietarioPorId() throws Exception {
        //ASSERT
        Long id = 1L;

        var resultado = mvc.perform(
                get("/proprietario/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, resultado.getStatus());
    }

    @Test
    void deveriaRetornar404AoBuscarProprietarioPorId() throws Exception {
        //ASSERT
        Long id = 1L;

        when(proprietarioService.buscarProprietarioPorId(id)).thenThrow(new ResourceNotFoundException("Proprietario não encontrado"));

        var resultado = mvc.perform(
                get("/proprietario/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404, resultado.getStatus());
    }

    @Test
    void deveriaRetornar409AoCadastrarProprietarioComEmailExistente() throws Exception {

        //ARRANGE
        String json = """
                {
                  "cnpj": "12.345.678/0001-90",
                  "razaoSocial": "Empresa Exemplo LTDA",
                  "nomeFantasia": "Empresa Exemplo",
                  "inscricaoEstadual": "123456789",
                  "telefoneComercial": "(11) 1234-5678",
                  "whatsapp": "(11) 91234-5678",
                  "statusConta": "ATIVO",
                  "nome": "Carlos Silva",
                  "email": "carlos@email.com",
                  "login": "carloss",
                  "senha": "senhaForte123",
                  "enderecos": [
                    {
                      "estado": "SP",
                      "cidade": "São Paulo",
                      "bairro": "Centro",
                      "rua": "Rua das Flores",
                      "numero": 100,
                      "complemento": "Sala 10",
                      "cep": "01000-000"
                    }
                  ]
                }
                """;

        //ACT
        doThrow(new DataIntegrityViolationException("Já existe um cadastro com este e-mail."))
                .when(proprietarioService)
                .cadastrarProprietario(any());

        var response = mvc.perform(
                post("/proprietario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(409, response.getStatus());
    }
}