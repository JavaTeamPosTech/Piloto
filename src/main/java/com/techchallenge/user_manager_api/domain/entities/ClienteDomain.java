package com.techchallenge.user_manager_api.domain.entities;

import com.techchallenge.user_manager_api.infra.model.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class ClienteDomain extends UsuarioDomain {

    private String cpf;

    private LocalDate dataNascimento;

    private GeneroEnum genero;

    private String telefone;

    private Set<TiposComidaEnum> preferenciasAlimentares = new HashSet<>();

    private Set<AlergiaAlimentarEnum> alergias = new HashSet<>();

    private MetodoPagamentoEnum metodoPagamentoPreferido;

    private Date ultimoPedido;

    private Integer saldoPontos;

    private Boolean clienteVip;

    private Integer avaliacoesFeitas;

    private Boolean notificacoesAtivas;


    public ClienteDomain(UUID id, String cpf, LocalDate dataNascimento, GeneroEnum genero, String telefone,
                         Set<TiposComidaEnum> preferenciasAlimentares, Set<AlergiaAlimentarEnum> alergias,
                         MetodoPagamentoEnum metodoPagamentoPreferido, Boolean clienteVip,
                         Boolean notificacoesAtivas, String nome, String email, String login, String senhaCriptografada) {

        super(id, nome, email, login, senhaCriptografada);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
        this.preferenciasAlimentares = preferenciasAlimentares != null
                ? new HashSet<>(preferenciasAlimentares)
                : new HashSet<>();
        this.alergias = alergias != null
                ? new HashSet<>(alergias)
                : new HashSet<>();
        this.metodoPagamentoPreferido = metodoPagamentoPreferido;
        this.clienteVip = clienteVip;
        this.notificacoesAtivas = notificacoesAtivas;
    }

}
