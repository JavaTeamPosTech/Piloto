package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.Usuario;
import com.techchallenge.user_manager_api.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.entities.enums.TiposComidaEnum;
import com.techchallenge.user_manager_api.exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Test
    void deveRealizarLoginComSucesso() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("usuario", "senha123");

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
                "SenhaForte123!",
                List.of());

        Authentication authentication = new UsernamePasswordAuthenticationToken(cliente, null);
        String token = "tokenJWT";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenService.generateToken(cliente.getLogin()))
                .thenReturn(token);

        LoginResponseDTO response = authorizationService.login(loginRequestDTO);

        assertNotNull(response);
        assertEquals(token, response.token());
    }


    @Test
    void deveLancarExcecaoAoRealizarLoginComCredenciaisInvalidas() {
        // Dados de login inválidos
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("usuario", "senhaIncorreta");

        // Simula falha na autenticação (credenciais erradas)
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Verifica se sua classe lança UnauthorizedException corretamente
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            authorizationService.login(loginRequestDTO);
        });

        // Mensagem opcional para garantir a semântica
        assertEquals("Login ou senha incorretos", exception.getMessage());
    }

}
