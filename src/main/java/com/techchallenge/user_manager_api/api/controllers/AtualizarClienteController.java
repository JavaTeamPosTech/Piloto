package com.techchallenge.user_manager_api.api.controllers;

import com.techchallenge.user_manager_api.application.inputs.AtualizarClienteInput;
import com.techchallenge.user_manager_api.application.usecases.cliente.AtualizarClienteUseCase;
import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarClienteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ClienteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Cliente Controller", description = "Operação Relacionada a atualização de Cliente")
@RequestMapping("/clientes")
public class AtualizarClienteController {

    private final AtualizarClienteUseCase atualizarClienteUseCase;


    public AtualizarClienteController(
                             AtualizarClienteUseCase atualizarClienteUseCase
                            ) {
        this.atualizarClienteUseCase = atualizarClienteUseCase;
    }

    @PreAuthorize("#id == authentication.principal.id")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Editar cliente", description = "Editar um Cliente pelo ID. Apenas o próprio Cliente pode executar.")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> editarCliente(
            @Parameter(description = "ID do Cliente a ser atualizado", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,
            @RequestBody @Valid AtualizarClienteRequestDTO clienteRequestDTO) {
        AtualizarClienteInput atualizarClienteInput = AtualizarClienteInput.from(id, clienteRequestDTO);
        ClienteResponseDTO response = atualizarClienteUseCase.executar(atualizarClienteInput);
        return ResponseEntity.ok(response);
    }

}
