package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(
            summary = "Realiza o cadastro de um novo usuáriodo do tipo Cliente",
            description = "Este endpoint cria um novo usuário do tipo Cliente no sistema, gerando um token JWT após o cadastro bem-sucedido"
    )
    @PostMapping
    public ResponseEntity<CadastroResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO){
        CadastroResponseDTO cadastroResponse = clienteService.cadastrarCliente(clienteRequestDTO);
        return ResponseEntity.ok(cadastroResponse);
    }

    @PreAuthorize("hasRole('PROPRIETARIO') or #id == authentication.principal.id")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Buscar cliente", description = "Busca os dados de um Cliente pelo ID. O próprio Cliente ou um Proprietário pode executar.")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable UUID id){
        return ResponseEntity.ok(clienteService.buscarCliente(id));
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Buscar todos os cliente", description = "Busca todos os clientes cadastrados. Somente um Proprietário pode executar.")
    @GetMapping()
    public ResponseEntity<List<ClienteResponseDTO>> buscarClientes(){
        return ResponseEntity.ok(clienteService.buscarClientes());
    }
}