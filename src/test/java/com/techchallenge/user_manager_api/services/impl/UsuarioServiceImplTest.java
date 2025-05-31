package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.Usuario;
import com.techchallenge.user_manager_api.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.entities.enums.TiposComidaEnum;
import com.techchallenge.user_manager_api.exceptions.UnauthorizedException;
import com.techchallenge.user_manager_api.repositories.UsuarioRepository;
import com.techchallenge.user_manager_api.services.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private Authentication authentication;

    @Mock
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioServiceImpl(usuarioRepository, passwordService);
    }

    @Test
    void deveAtualizarSenhaComSucesso() {
        // Arrange: cliente com senha criptografada antiga (como se estivesse no banco)
        Cliente cliente = new Cliente(
                "123.456.789-00",
                LocalDate.now(),
                GeneroEnum.MASCULINO,
                "+5581999992345",
                Set.of(TiposComidaEnum.CHURRASCO, TiposComidaEnum.JAPONESA),
                Set.of(AlergiaAlimentarEnum.GLUTEN),
                MetodoPagamentoEnum.PIX,
                true,
                true,
                "João da Silva",
                "joaodasilva@email.com",
                "joaodasilva",
                "senhaCriptografadaAntiga", // senha atual criptografada
                List.of()
        );

        Authentication auth = new UsernamePasswordAuthenticationToken(cliente, null);
        AtualizarSenhaRequestDTO requestDTO = new AtualizarSenhaRequestDTO("senhaAtual", "novaSenha");

        // Mockando comparação e criptografia
        when(passwordService.matches("senhaAtual", "senhaCriptografadaAntiga")).thenReturn(true);
        when(passwordService.encryptPassword("novaSenha")).thenReturn("novaSenhaCriptografada");

        // Act
        usuarioService.atualizarSenha(requestDTO, auth);

        // Assert
        assertEquals("novaSenhaCriptografada", cliente.getSenha());
        verify(usuarioRepository).save(cliente);
    }



    @Test
    void deveLancarUnauthorizedExceptionQuandoSenhaAtualEstiverIncorreta() {
        // Arrange
        Cliente cliente = new Cliente(
                "123.456.789-00",
                LocalDate.now(),
                GeneroEnum.MASCULINO,
                "+5581999992345",
                Set.of(TiposComidaEnum.CHURRASCO),
                Set.of(AlergiaAlimentarEnum.GLUTEN),
                MetodoPagamentoEnum.PIX,
                true,
                true,
                "João da Silva",
                "joao@email.com",
                "joaodasilva",
                "senhaCorretaCriptografada", // senha atual salva
                List.of()
        );

        Authentication auth = new UsernamePasswordAuthenticationToken(cliente, null);
        AtualizarSenhaRequestDTO dto = new AtualizarSenhaRequestDTO("senhaErrada", "novaSenha");

        when(passwordService.matches("senhaErrada", "senhaCorretaCriptografada")).thenReturn(false);

        // Act + Assert
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
                usuarioService.atualizarSenha(dto, auth)
        );

        assertEquals("Senha atual incorreta", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

}
