package com.techchallenge.user_manager_api.application.inputs;

import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarClienteRequestDTO;
import com.techchallenge.user_manager_api.infra.model.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.infra.model.enums.GeneroEnum;
import com.techchallenge.user_manager_api.infra.model.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.infra.model.enums.TiposComidaEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class AtualizarClienteInput {
    private UUID id;
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private LocalDate dataNascimento;
    private GeneroEnum genero;
    private String telefone;
    private Set<TiposComidaEnum> preferenciasAlimentares;
    private Set<AlergiaAlimentarEnum> alergias;
    private MetodoPagamentoEnum metodoPagamentoPreferido;
    private Boolean notificacoesAtivas;
    private List<EnderecoInput> enderecos;

    private AtualizarClienteInput(
            UUID id,
            String nome,
            String cpf,
            String email,
            String login,
            LocalDate dataNascimento,
            GeneroEnum genero,
            String telefone,
            Set<TiposComidaEnum> preferenciasAlimentares,
            Set<AlergiaAlimentarEnum> alergias,
            MetodoPagamentoEnum metodoPagamentoPreferido,
            Boolean notificacoesAtivas,
            List<EnderecoInput> enderecos
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.login = login;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
        this.preferenciasAlimentares = preferenciasAlimentares;
        this.alergias = alergias;
        this.metodoPagamentoPreferido = metodoPagamentoPreferido;
        this.notificacoesAtivas = notificacoesAtivas;
        this.enderecos = enderecos;
    }

    public static AtualizarClienteInput from(UUID id, AtualizarClienteRequestDTO dto) {
        List<EnderecoInput> enderecosInput = dto.enderecos() == null ? List.of() :
                dto.enderecos()
                        .stream()
                        .map(EnderecoInput::from)
                        .collect(Collectors.toList());

        return new AtualizarClienteInput(
                id,
                dto.nome(),
                dto.cpf(),
                dto.email(),
                dto.login(),
                dto.dataNascimento(),
                dto.genero(),
                dto.telefone(),
                dto.preferenciasAlimentares(),
                dto.alergias(),
                dto.metodoPagamentoPreferido(),
                dto.notificacoesAtivas(),
                enderecosInput
        );
    }


}
