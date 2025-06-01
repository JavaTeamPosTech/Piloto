package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.entities.Proprietario;
import com.techchallenge.user_manager_api.entities.enums.*;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.repositories.ProprietarioRepository;
import com.techchallenge.user_manager_api.services.PasswordService;
import com.techchallenge.user_manager_api.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProprietarioServiceImplTest {

    @Mock
    private ProprietarioRepository proprietarioRepository;

    @Captor
    private ArgumentCaptor<Proprietario> proprietarioArgumentCaptor;

    private ProprietarioServiceImpl proprietarioService;

    private ProprietarioRequestDTO proprietarioRequestDTO;

    private EnderecoRequestDTO enderecoRequestDTO;

    private List<EnderecoRequestDTO> enderecos;

    @Mock
    TokenService tokenService;

    @Mock
    UsuarioService usuarioService;

    @Mock
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        proprietarioService = new ProprietarioServiceImpl(proprietarioRepository, passwordService,  tokenService,  usuarioService);
    }



    @Test
    void deveRetornarSucessoCadastrarProprietario() {
        this.proprietarioRequestDTO = new ProprietarioRequestDTO(
                "12.345.678/0001-90",
                "Empresa Exemplo LTDA",
                "Empresa Exemplo",
                "123456789",
                "(11) 1234-5678",
                "(11) 91234-5678",
                StatusContaEnum.ATIVO,
                "Carlos Silva",
                "carlos.silva@email.com",
                "carloss",
                "senhaForte123",
                List.of(
                        new EnderecoRequestDTO(
                                "SP",
                                "São Paulo",
                                "Centro",
                                "Rua das Flores",
                                100,
                                "Sala 10",
                                "01000-000"
                        )
                )
        );

        when(proprietarioRepository.save(any(Proprietario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        proprietarioService.cadastrarProprietario(proprietarioRequestDTO);

        then(proprietarioRepository).should().save(proprietarioArgumentCaptor.capture());
        Proprietario proprietario = proprietarioArgumentCaptor.getValue();

        assertEquals("12.345.678/0001-90", proprietario.getCnpj());
        assertEquals("carlos.silva@email.com", proprietario.getEmail());
    }

    @Test
    void deveriaRetornarErroAoCadastrarProprietarioComCnpjExistente() {
        this.proprietarioRequestDTO = new ProprietarioRequestDTO(
                "12.345.678/0001-90",
                "Empresa Exemplo LTDA",
                "Empresa Exemplo",
                "123456789",
                "(11) 1234-5678",
                "(11) 91234-5678",
                StatusContaEnum.ATIVO,
                "Carlos Silva",
                "carlos.silva@email.com",
                "carloss",
                "senhaForte123",
                List.of(
                        new EnderecoRequestDTO(
                                "SP",
                                "São Paulo",
                                "Centro",
                                "Rua das Flores",
                                100,
                                "Sala 10",
                                "01000-000"
                        )
                )
        );

        when(proprietarioRepository.save(any())).thenThrow(new DataIntegrityViolationException("Já existe um cadastro com este CNPJ."));

        assertThrows(DataIntegrityViolationException.class, () -> {
            proprietarioService.cadastrarProprietario(proprietarioRequestDTO);
        });
    }

    @Test
    void deveriaRetornarErroAoCadastrarProprietarioComEmailExistente() {
        this.proprietarioRequestDTO = new ProprietarioRequestDTO(
                "12.345.678/0001-90",
                "Empresa Exemplo LTDA",
                "Empresa Exemplo",
                "123456789",
                "(11) 1234-5678",
                "(11) 91234-5678",
                StatusContaEnum.ATIVO,
                "Carlos Silva",
                "carlos.silva@email.com",
                "carloss",
                "senhaForte123",
                List.of(
                        new EnderecoRequestDTO(
                                "SP",
                                "São Paulo",
                                "Centro",
                                "Rua das Flores",
                                100,
                                "Sala 10",
                                "01000-000"
                        )
                )
        );

        when(proprietarioRepository.save(any())).thenThrow(new DataIntegrityViolationException("Já existe um cadastro com este e-mail."));

        assertThrows(DataIntegrityViolationException.class, () -> {
            proprietarioService.cadastrarProprietario(proprietarioRequestDTO);
        });
    }

    @Test
    void deveriaRetornarSucessoAoBuscarClientePorId() {
        // ARRANGE
        UUID id = UUID.randomUUID();

        Proprietario proprietarioMock = mock(Proprietario.class);
        when(proprietarioMock.getId()).thenReturn(id);
        when(proprietarioMock.getEmail()).thenReturn("joao@email.com");
        when(proprietarioMock.getCnpj()).thenReturn("12.345.678/0001-90");

        when(proprietarioRepository.findById(id)).thenReturn(Optional.of(proprietarioMock));

        // ACT
        ProprietarioResponseDTO resultado = proprietarioService.buscarProprietarioPorId(id);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("joao@email.com", resultado.email());
        assertEquals("12.345.678/0001-90", resultado.cnpj());
        assertEquals(id, resultado.id());
    }
    @Test
    void deveriaRetornarErroAoBuscarClientePorId() throws Exception {
        // ARRANGE
        UUID id = UUID.randomUUID();

        //ACT
        when(proprietarioRepository.findById(id)).thenReturn(Optional.empty());

        //ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            proprietarioService.buscarProprietarioPorId(id);
        });
    }

}