package com.techchallenge.user_manager_api.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Usuário Controller", description = "Operações relacionadas ao Usuário (Cliente ou Proprietário)")
@RequestMapping("/usuarios")
public class UsuarioController {

//    private final UsuarioService usuarioService;
//    private final AuthorizationServiceImpl authorizationServiceImpl;
//
//    public UsuarioController(UsuarioService usuarioService, AuthorizationServiceImpl authorizationServiceImpl) {
//        this.usuarioService = usuarioService;
//        this.authorizationServiceImpl = authorizationServiceImpl;
//    }
//
//    @Operation(summary = "Atualizar senha", description = "Permite o usuário logado atualizar sua senha.")
//    @SecurityRequirement(name = "bearerAuth")
//    @PutMapping("/atualizar-senha")
//    public ResponseEntity<Void> atualizarSenha(@RequestBody @Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO, Authentication authentication) {
//        usuarioService.atualizarSenha(atualizarSenhaRequestDTO, authentication);
//        return ResponseEntity.noContent().build();
//    }
//
//    @Operation(
//            summary = "Realiza o login de um usuário",
//            description = "Este endpoint permite que um usuário faça login no sistema, retornando um token JWT se as credenciais forem válidas."
//    )
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
//        return ResponseEntity.ok(authorizationServiceImpl.login(loginRequestDTO));
//    }
}
