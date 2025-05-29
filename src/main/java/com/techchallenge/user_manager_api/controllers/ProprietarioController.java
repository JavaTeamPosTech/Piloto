package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.services.ProprietarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Proprietário Controller", description = "Operações relacionadas ao Proprietário")
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    public ProprietarioController(ProprietarioService proprietarioService) {
        this.proprietarioService = proprietarioService;
    }

    @Operation(
            summary = "Realiza o cadastro de um novo usuáriodo do tipo Proprietário",
            description = "Este endpoint cria um novo usuário do tipo Proprietário no sistema, gerando um token JWT após o cadastro bem-sucedido"
    )
    @PostMapping
    public ResponseEntity<CadastroResponseDTO> cadastrarProprietario(@RequestBody @Valid ProprietarioRequestDTO proprietarioDTO) {
        CadastroResponseDTO cadastroResponse = proprietarioService.cadastrarProprietario(proprietarioDTO);
        return ResponseEntity.ok(cadastroResponse);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Buscar proprietário", description = "Busca os dados de um Proprietário pelo ID. Somente um Proprietário pode executar.")
    @GetMapping("/{id}")
    public ResponseEntity<ProprietarioResponseDTO> buscarProprietarioPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(proprietarioService.buscarProprietarioPorId(id));
    }
}
