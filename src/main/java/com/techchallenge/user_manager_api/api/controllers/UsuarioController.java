package com.techchallenge.user_manager_api.api.controllers;

import com.techchallenge.user_manager_api.application.usecases.usuario.AtualizarSenhaUseCase;
import com.techchallenge.user_manager_api.application.usecases.usuario.LoginUseCase;
import com.techchallenge.user_manager_api.application.usecases.usuario.UsuarioPresenter;
import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.infra.security.authorization.impl.AuthorizationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Usuário Controller", description = "Operações relacionadas ao Usuário (Cliente ou Proprietário)")
@RequestMapping("/usuarios")
public class UsuarioController {

    //private final UsuarioService usuarioService;
    private final AuthorizationServiceImpl authorizationServiceImpl;
    private final LoginUseCase loginUseCase;
    private final AtualizarSenhaUseCase atualizarSenhaUseCase;
    private final UsuarioPresenter usuarioPresenter;

    public UsuarioController(AuthorizationServiceImpl authorizationServiceImpl, LoginUseCase loginUseCase, AtualizarSenhaUseCase atualizarSenhaUseCase, UsuarioPresenter usuarioPresenter) {
        this.authorizationServiceImpl = authorizationServiceImpl;
        this.loginUseCase = loginUseCase;
        this.atualizarSenhaUseCase = atualizarSenhaUseCase;
        this.usuarioPresenter = usuarioPresenter;
    }

    @Operation(summary = "Atualizar senha", description = "Permite o usuário logado atualizar sua senha.")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/atualizar-senha")
    public ResponseEntity<Void> atualizarSenha(@RequestBody @Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO, Authentication authentication) {
        atualizarSenhaUseCase.atualizarSenha(atualizarSenhaRequestDTO, authentication);
        //usuarioService.atualizarSenha(atualizarSenhaRequestDTO, authentication);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Realiza o login de um usuário",
            description = "Este endpoint permite que um usuário faça login no sistema, retornando um token JWT se as credenciais forem válidas."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        loginUseCase.login(loginRequestDTO);
        return ResponseEntity.ok(usuarioPresenter.getViewModel());
    }
}
