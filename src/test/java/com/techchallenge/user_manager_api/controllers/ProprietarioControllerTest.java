package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.services.ProprietarioService;
import com.techchallenge.user_manager_api.services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "joaolima123", roles = {"PROPRIETARIO"})
class ProprietarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProprietarioService proprietarioService;

    @BeforeEach
    void setupSecurityContext() {
        UUID uuidProprietario = UUID.fromString("11111111-1111-1111-1111-111111111111");

        var principalMock = new Object() {
            public UUID id = uuidProprietario;
        };

        var auth = new UsernamePasswordAuthenticationToken(
                principalMock,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_PROPRIETARIO"))
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void deveriaRetornar200AoCadastrarProprietario() throws Exception {
        // ARRANGE
        String json = """
                {
                   "cnpj": "12.345.678/0001-95",
                   "razaoSocial": "Empresa de Exemplo LTDA",
                   "nomeFantasia": "Exemplo Comércio",
                   "inscricaoEstadual": "1234567890",
                   "telefoneComercial": "+5581999999999",
                   "whatsapp": "+5581999999999",
                   "statusConta": "ATIVO",
                   "nome": "João Lima",
                   "email": "joaolima@empresa.com",
                   "login": "joaolima",
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
                "João Lima",
                "joaolima@empresa.com",
                "joaolima");
        CadastroResponseDTO cadastroResponseDTO = new CadastroResponseDTO(usuarioResponseDTO,"");


        when(proprietarioService.cadastrarProprietario(any())).thenReturn(cadastroResponseDTO);

        //ACT
        var response = mvc.perform(
                        post("/proprietarios")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400AoCadastrarProprietarioVazio() throws Exception {
        //ARRANGE
        String json = "{}";
        //ACT
        var response = mvc.perform(
                post("/proprietarios")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400AoCadastrarProprietarioSemCnpjEEmail() throws Exception {
        // ARRANGE
        String json = """
                {
                   "cnpj": "",
                   "razaoSocial": "Empresa de Exemplo LTDA",
                   "nomeFantasia": "Exemplo Comércio",
                   "inscricaoEstadual": "1234567890",
                   "telefoneComercial": "+5581999999999",
                   "whatsapp": "+5581999999999",
                   "statusConta": "ATIVO",
                   "nome": "João Lima",
                   "email": "",
                   "login": "joaolima",
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
                post("/proprietarios")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200AoBuscarProprietarioPorId() throws Exception {
        UUID uuidProprietario = UUID.fromString("11111111-1111-1111-1111-111111111111");

        ProprietarioResponseDTO proprietarioResponseDTO = new ProprietarioResponseDTO(
                uuidProprietario,
                "12.345.678/0001-95",
                "Empresa de Exemplo LTDA",
                "Exemplo Comércio",
                "1234567890",
                "+5581999999999",
                "+5581999999999",
                "João Lima",
                "rafael@gmail.com",
                "rafaelLogin123",
                List.of()
        );



        when(proprietarioService.buscarProprietarioPorId(uuidProprietario)).thenReturn(proprietarioResponseDTO);

        mvc.perform(get("/proprietarios/" + uuidProprietario))
                .andExpect(status().isOk());
    }


    @Test
    void deveriaRetornar404AoBuscarProprietarioInexistente() throws Exception {
        UUID uuidProprietario = UUID.fromString("11111111-1111-1111-1111-111111111111");

        when(proprietarioService.buscarProprietarioPorId(uuidProprietario))
                .thenThrow(new ResourceNotFoundException("Proprietario não encontrado"));

        var response = mvc.perform(get("/proprietarios/" + uuidProprietario))
                .andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornar409AoCadastrarProprietarioComCnpjExistente() throws Exception {
        //ARRANGE
        String json = """
                {
                   "cnpj": "12.345.678/0001-95",
                   "razaoSocial": "Empresa de Exemplo LTDA",
                   "nomeFantasia": "Exemplo Comércio",
                   "inscricaoEstadual": "1234567890",
                   "telefoneComercial": "+5581999999999",
                   "whatsapp": "+5581999999999",
                   "statusConta": "ATIVO",
                   "nome": "João Lima",
                   "email": "joaolima@empresa.com",
                   "login": "joaolima",
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
        doThrow(new DataIntegrityViolationException("Já existe um cadastro com este CNPJ."))
                .when(proprietarioService)
                .cadastrarProprietario(any());

        var response = mvc.perform(
                post("/proprietarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(409, response.getStatus());
    }

    @Test
    void deveriaRetornar409AoCadastrarProprietarioComEmailExistente() throws Exception {
        //ARRANGE
        String json = """
                {
                   "cnpj": "12.345.678/0001-95",
                   "razaoSocial": "Empresa de Exemplo LTDA",
                   "nomeFantasia": "Exemplo Comércio",
                   "inscricaoEstadual": "1234567890",
                   "telefoneComercial": "+5581999999999",
                   "whatsapp": "+5581999999999",
                   "statusConta": "ATIVO",
                   "nome": "João Lima",
                   "email": "joaolima@empresa.com",
                   "login": "joaolima",
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
        doThrow(new DataIntegrityViolationException("Já existe um cadastro com este e-mail."))
                .when(proprietarioService)
                .cadastrarProprietario(any());

        var response = mvc.perform(
                post("/proprietarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(409, response.getStatus());
    }


}