package com.techchallenge.user_manager_api.api.controllers;

import com.techchallenge.user_manager_api.application.usecases.proprietario.BuscarProprietarioPorIdUseCase;
import com.techchallenge.user_manager_api.application.usecases.proprietario.CriacaoDeProprietarioUseCase;
import com.techchallenge.user_manager_api.application.usecases.proprietario.DeletarProprietarioPorIdUseCase;
import com.techchallenge.user_manager_api.application.usecases.proprietario.ProprietarioPresenter;
import com.techchallenge.user_manager_api.domain.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Proprietário Controller", description = "Operações relacionadas ao Proprietário")
@RequestMapping("/proprietarios")
public class ProprietarioController {

    //private final ProprietarioService proprietarioService;
    private final CriacaoDeProprietarioUseCase criacaoDeProprietarioUseCase;
    private final ProprietarioPresenter proprietarioPresenter;
    private final BuscarProprietarioPorIdUseCase buscarProprietarioPorIdUseCase;
    private final DeletarProprietarioPorIdUseCase deletarProprietarioPorIdUseCase;

    public ProprietarioController(CriacaoDeProprietarioUseCase criacaoDeProprietarioUseCase, ProprietarioPresenter proprietarioPresenter,
                                  BuscarProprietarioPorIdUseCase buscarProprietarioPorIdUseCase, DeletarProprietarioPorIdUseCase deletarProprietarioPorIdUseCase) {
        this.criacaoDeProprietarioUseCase = criacaoDeProprietarioUseCase;
        this.proprietarioPresenter = proprietarioPresenter;
        this.buscarProprietarioPorIdUseCase =  buscarProprietarioPorIdUseCase;
        this.deletarProprietarioPorIdUseCase =  deletarProprietarioPorIdUseCase;
    }

    @Operation(
            summary = "Realiza o cadastro de um novo usuáriodo do tipo Proprietário",
            description = "Este endpoint cria um novo usuário do tipo Proprietário no sistema, gerando um token JWT após o cadastro bem-sucedido"
    )
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarProprietario(@RequestBody @Valid ProprietarioRequestDTO proprietarioDTO) {
        criacaoDeProprietarioUseCase.cadastrarProprietario(proprietarioDTO);
        return ResponseEntity.ok(proprietarioPresenter.getViewModel());
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Buscar proprietário", description = "Busca os dados de um Proprietário pelo ID. Somente um Proprietário pode executar.")
    @GetMapping("/{id}")
    public ResponseEntity<ProprietarioResponseDTO> buscarProprietarioPorId(
            @Parameter(description = "ID do Proprietário a ser procurado", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(buscarProprietarioPorIdUseCase.executar(id));
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Deletar proprietário", description = "Deleta um Proprietário pelo ID. Somente um Proprietário pode executar.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProprietario(
            @Parameter(description = "ID do Proprietário a ser deletado", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id
    ) {
        deletarProprietarioPorIdUseCase.executar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
//
//    @PreAuthorize("#id == authentication.principal.id")
//    @SecurityRequirement(name = "bearerAuth")
//    @Operation(summary = "Editar proprietário", description = "Editar um Proprietário pelo ID. Apenas o próprio Proprietário pode executar.")
//    @PutMapping("/{id}")
//    public ResponseEntity<ProprietarioResponseDTO> editarProprietario(
//            @Parameter(description = "ID do Proprietário a ser atualizado", example = "550e8400-e29b-41d4-a716-446655440000")
//            @PathVariable UUID id,
//            @RequestBody @Valid AtualizarProprietarioRequestDTO proprietarioRequestDTO){
//        return ResponseEntity.ok(proprietarioService.editarProprietario(id, proprietarioRequestDTO));
//    }
}
