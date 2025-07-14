package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.naousar.controllers.ProprietarioController;
import com.techchallenge.user_manager_api.naousar.dto.requests.AtualizarProprietarioRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.naousar.entities.enums.StatusContaEnum;
import com.techchallenge.user_manager_api.naousar.services.ProprietarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioControllerTest {

    @InjectMocks
    private ProprietarioController proprietarioController;

    @Mock
    private ProprietarioService proprietarioService;

    private CadastroResponseDTO cadastroResponseDTO;
    private ProprietarioResponseDTO proprietarioResponseDTO;
    private ProprietarioRequestDTO proprietarioRequestDTO;

    @BeforeEach
    void setUp() {
        proprietarioService = Mockito.mock(ProprietarioService.class);
        proprietarioController = new ProprietarioController(proprietarioService);

        proprietarioRequestDTO = new ProprietarioRequestDTO(
                "12.345.678/0001-95",
                "Empresa de Exemplo LTDA",
                "Exemplo Comércio",
                "1234567890",
                "+5581999999999",
                "+5581999999999",
                StatusContaEnum.ATIVO,
                "João Lima",
                "joaolima@empresa.com",
                "joaolima",
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
        cadastroResponseDTO = new CadastroResponseDTO(
                new UsuarioResponseDTO(
                        UUID.randomUUID(),
                        "João Lima",
                        "joaolima@empresa.com",
                        "joaolima"
                ),
                "token-jwt"
        );

        proprietarioResponseDTO = new ProprietarioResponseDTO(
                UUID.randomUUID(),
                "12.345.678/0001-95",
                "Empresa de Exemplo LTDA",
                "Exemplo Comércio",
                "1234567890",
                "+5581999999999",
                "+5581999999999",
                "João Lima",
                "joaolima@empresa.com",
                "joaolima",
                List.of()
        );
    }

    @Test
    void deveriaCadastrarProprietarioComSucesso() {
        when(proprietarioService.cadastrarProprietario(proprietarioRequestDTO)).thenReturn(cadastroResponseDTO);

        ResponseEntity<CadastroResponseDTO> response = proprietarioController.cadastrarProprietario(proprietarioRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cadastroResponseDTO, response.getBody());
    }

    @Test
    void deveriaBuscarProprietarioPorIdComSucesso() {
        UUID idProprietario = UUID.randomUUID();
        when(proprietarioService.buscarProprietarioPorId(idProprietario)).thenReturn(proprietarioResponseDTO);

        ResponseEntity<ProprietarioResponseDTO> response = proprietarioController.buscarProprietarioPorId(idProprietario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(proprietarioResponseDTO, response.getBody());
    }

    @Test
    void deveriaRetornarErroAoBuscarProprietarioPorIdInexistente() {
        UUID idProprietario = UUID.randomUUID();
        when(proprietarioService.buscarProprietarioPorId(idProprietario)).thenThrow(new RuntimeException("Proprietário não encontrado"));

        assertThrows(RuntimeException.class, () -> proprietarioController.buscarProprietarioPorId(idProprietario));
    }

    @Test
    void deveriaDeletarProprietarioComSucesso() {
        UUID idProprietario = UUID.randomUUID();

        ResponseEntity<Void> response = proprietarioController.deletarProprietario(idProprietario);

        verify(proprietarioService, times(1)).deletarProprietario(idProprietario);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deveriaRetornarErroAoDeletarProprietarioInexistente() {
        UUID idProprietario = UUID.randomUUID();
        doThrow(new RuntimeException("Proprietário não encontrado")).when(proprietarioService).deletarProprietario(idProprietario);

        assertThrows(RuntimeException.class, () -> proprietarioController.deletarProprietario(idProprietario));
    }

    @Test
    void deveriaEditarProprietarioComSucesso() {
        UUID idProprietario = UUID.randomUUID();
        AtualizarProprietarioRequestDTO atualizarProprietarioRequestDTO = new AtualizarProprietarioRequestDTO(
                "12.345.678/0001-95",
                "Empresa de Exemplo LTDA",
                "Exemplo Comércio",
                "1234567890",
                "+5581999999999",
                "+5581999999999",
                StatusContaEnum.ATIVO,
                "João Lima",
                "joaolima@empresa.com",
                "joaolima",
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
        when(proprietarioService.editarProprietario(idProprietario, atualizarProprietarioRequestDTO)).thenReturn(proprietarioResponseDTO);
        ResponseEntity<ProprietarioResponseDTO> response = proprietarioController.editarProprietario(idProprietario, atualizarProprietarioRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(proprietarioResponseDTO, response.getBody());
    }
}