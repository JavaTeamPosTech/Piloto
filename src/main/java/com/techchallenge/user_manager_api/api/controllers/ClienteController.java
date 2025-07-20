package com.techchallenge.user_manager_api.api.controllers;

import com.techchallenge.user_manager_api.application.usecases.BuscarClienteUseCase;
import com.techchallenge.user_manager_api.application.usecases.CriacaoDeClienteUseCase;
import com.techchallenge.user_manager_api.application.usecases.cliente.AtualizarClienteUseCase;
import com.techchallenge.user_manager_api.application.usecases.cliente.BuscarTodosClientesUseCase;
import com.techchallenge.user_manager_api.application.usecases.presenters.ClientePresenter;
import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarClienteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Cliente Controller", description = "Operações relacionadas ao Cliente")
@RequestMapping("/clientes")
public class ClienteController {

    //private final ClienteService clienteService;
    private final CriacaoDeClienteUseCase criacaoDeClienteUseCase;
    private final ClientePresenter clientePresenter;
    private final BuscarClienteUseCase buscarClienteUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;
    private final BuscarTodosClientesUseCase buscarTodosClientesUseCase;

    public ClienteController(CriacaoDeClienteUseCase criacaoDeClienteUseCase,
                             ClientePresenter clientePresenter,  BuscarClienteUseCase buscarClienteUseCase,
                             AtualizarClienteUseCase atualizarClienteUseCase, BuscarTodosClientesUseCase buscarTodosClientesUseCase ) {
        this.clientePresenter = clientePresenter;
        this.criacaoDeClienteUseCase = criacaoDeClienteUseCase;
        this.buscarClienteUseCase = buscarClienteUseCase;
        this.atualizarClienteUseCase = atualizarClienteUseCase;
        this.buscarTodosClientesUseCase = buscarTodosClientesUseCase;
    }

    @Operation(
            summary = "Realiza o cadastro de um novo usuáriodo do tipo Cliente",
            description = "Este endpoint cria um novo usuário do tipo Cliente no sistema, gerando um token JWT após o cadastro bem-sucedido"
    )
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        criacaoDeClienteUseCase.cadastrarCliente(clienteRequestDTO);
        return ResponseEntity.ok(clientePresenter.getViewModel());

//        CadastroResponseDTO cadastroResponse = clienteService.cadastrarCliente(clienteRequestDTO);
//        return ResponseEntity.ok(cadastroResponse);
    }


    @PreAuthorize("hasRole('PROPRIETARIO') or #id == authentication.principal.id")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Buscar cliente", description = "Busca os dados de um Cliente pelo ID. O próprio Cliente ou um Proprietário pode executar.")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(
            @Parameter(description = "ID do Cliente a ser procurado", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {


        ClienteDomain cliente = buscarClienteUseCase.buscarClientePorId(id);
        return ResponseEntity.ok(clientePresenter.retornarCliente(cliente));
    }

    @PreAuthorize("#id == authentication.principal.id")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Editar cliente", description = "Editar um Cliente pelo ID. Apenas o próprio Cliente pode executar.")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> editarCliente(
            @Parameter(description = "ID do Cliente a ser atualizado", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,
            @RequestBody @Valid AtualizarClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO response = atualizarClienteUseCase.executar(id, clienteRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Buscar todos os cliente", description = "Busca todos os clientes cadastrados. Somente um Proprietário pode executar.")
    @GetMapping()
    public ResponseEntity<List<ClienteResponseDTO>> buscarClientes() {
        return ResponseEntity.ok(buscarTodosClientesUseCase.buscarClientes());
    }

//    @PreAuthorize("hasRole('PROPRIETARIO') or #id == authentication.principal.id")
//    @SecurityRequirement(name = "bearerAuth")
//    @Operation(summary = "Deletar cliente", description = "Deletar um Cliente pelo ID. Apenas o Proprietário ou próprio Cliente pode executar.")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletarCliente(
//            @Parameter(description = "ID do Cliente a ser deletado", example = "550e8400-e29b-41d4-a716-446655440000")
//            @PathVariable UUID id) {
//        clienteService.deletarCliente(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

}