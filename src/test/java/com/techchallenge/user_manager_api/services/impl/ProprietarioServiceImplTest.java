package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.AtualizarProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.entities.Proprietario;
import com.techchallenge.user_manager_api.entities.enums.StatusContaEnum;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.repositories.ProprietarioRepository;
import com.techchallenge.user_manager_api.services.PasswordService;
import com.techchallenge.user_manager_api.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioServiceImplTest {

    @Mock
    private ProprietarioRepository proprietarioRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ProprietarioServiceImpl proprietarioService;

    private ProprietarioRequestDTO proprietarioRequestDTO;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void cadastrarProprietario_CadastroBemSucedido() {
        Proprietario proprietario = new Proprietario();
        proprietario.setLogin("joaolima");
        when(usuarioService.existsByLogin(proprietarioRequestDTO.login())).thenReturn(false);
        when(passwordService.encryptPassword(proprietarioRequestDTO.senha())).thenReturn("senhaCriptografada");
        when(proprietarioRepository.save(any(Proprietario.class))).thenReturn(proprietario);
        when(tokenService.generateToken(proprietario.getLogin())).thenReturn("token");

        CadastroResponseDTO response = proprietarioService.cadastrarProprietario(proprietarioRequestDTO);

        assertNotNull(response);
        assertEquals("joaolima", response.usuario().login());
        assertEquals("token", response.token());
        verify(proprietarioRepository, times(1)).save(any(Proprietario.class));
    }

    @Test
    void cadastrarProprietario_LoginJaEmUso() {
        when(usuarioService.existsByLogin(proprietarioRequestDTO.login())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> proprietarioService.cadastrarProprietario(proprietarioRequestDTO));
        verify(proprietarioRepository, never()).save(any(Proprietario.class));
    }

    @Test
    void buscarProprietarioPorId_ProprietarioEncontrado() {
        UUID id = UUID.randomUUID();
        Proprietario proprietario = new Proprietario();
        proprietario.setLogin("joaolima");
        when(proprietarioRepository.findById(id)).thenReturn(Optional.of(proprietario));

        ProprietarioResponseDTO response = proprietarioService.buscarProprietarioPorId(id);

        assertNotNull(response);
        assertEquals("joaolima", response.login());
        verify(proprietarioRepository, times(1)).findById(id);
    }

    @Test
    void buscarProprietarioPorId_ProprietarioNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(proprietarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> proprietarioService.buscarProprietarioPorId(id));
        verify(proprietarioRepository, times(1)).findById(id);
    }

    @Test
    void editarProprietario_AtualizacaoBemSucedida() {
        UUID id = UUID.randomUUID();
        AtualizarProprietarioRequestDTO atualizarProprietarioRequestDTO = new AtualizarProprietarioRequestDTO(
                "12.345.678/0001-95",
                "Empresa Atualizada LTDA",
                "Atualizado Comércio",
                "1234567890",
                "+5581999999999",
                "+5581999999999",
                StatusContaEnum.ATIVO,
                "João Lima Atualizado",
                "joaolima@empresa.com",
                "joaolimaAtualizado",
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

        Proprietario proprietario = new Proprietario();
        proprietario.setLogin("joaolima");
        when(proprietarioRepository.findById(id)).thenReturn(Optional.of(proprietario));
        when(usuarioService.existsByLogin(atualizarProprietarioRequestDTO.login())).thenReturn(false);
        when(proprietarioRepository.save(any(Proprietario.class))).thenReturn(proprietario);

        ProprietarioResponseDTO response = proprietarioService.editarProprietario(id, atualizarProprietarioRequestDTO);

        assertNotNull(response);
        assertEquals("joaolimaAtualizado", response.login());
        verify(proprietarioRepository, times(1)).save(any(Proprietario.class));
    }

    @Test
    void editarProprietario_LoginJaEmUso() {
        UUID id = UUID.randomUUID();
        AtualizarProprietarioRequestDTO atualizarProprietarioRequestDTO = new AtualizarProprietarioRequestDTO(
                "12.345.678/0001-95",
                "Empresa Atualizada LTDA",
                "Atualizado Comércio",
                "1234567890",
                "+5581999999999",
                "+5581999999999",
                StatusContaEnum.ATIVO,
                "João Lima Atualizado",
                "joaolima@empresa.com",
                "joaolimaAtualizado",
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

        Proprietario proprietario = new Proprietario();
        proprietario.setLogin("joaolima");
        when(proprietarioRepository.findById(id)).thenReturn(Optional.of(proprietario));
        when(usuarioService.existsByLogin(atualizarProprietarioRequestDTO.login())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> proprietarioService.editarProprietario(id, atualizarProprietarioRequestDTO));
        verify(proprietarioRepository, never()).save(any(Proprietario.class));
    }

    @Test
    void deletarProprietario_ProprietarioRemovido() {
        UUID id = UUID.randomUUID();
        Proprietario proprietario = new Proprietario();
        when(proprietarioRepository.findById(id)).thenReturn(Optional.of(proprietario));

        proprietarioService.deletarProprietario(id);

        verify(proprietarioRepository, times(1)).deleteById(id);
    }

    @Test
    void deletarProprietario_ProprietarioNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(proprietarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> proprietarioService.deletarProprietario(id));
        verify(proprietarioRepository, never()).deleteById(any(UUID.class));
    }

    //Adicoes teste
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
}