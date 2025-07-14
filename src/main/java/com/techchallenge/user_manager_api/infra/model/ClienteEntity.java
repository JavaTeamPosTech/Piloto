package com.techchallenge.user_manager_api.infra.model;

import com.techchallenge.user_manager_api.infra.model.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "clientes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cliente_cpf", columnNames = "cpf"),
                @UniqueConstraint(name = "uk_cliente_telefone", columnNames = "telefone")
        })
public class ClienteEntity extends UsuarioEntity {

    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;

    private String telefone;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "preferencias_alimentares")
    private Set<TiposComidaEnum> preferenciasAlimentares = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AlergiaAlimentarEnum> alergias = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento_preferido")
    private MetodoPagamentoEnum metodoPagamentoPreferido;

    private Date ultimoPedido;

    @Column(name = "saldo_pontos")
    private Integer saldoPontos;

    @Column(name = "cliente_vip")
    private Boolean clienteVip;

    @Column(name = "avaliacoes_feitas")
    private Integer avaliacoesFeitas;

    @Column(name = "notificacoes_ativas")
    private Boolean notificacoesAtivas;

    public ClienteEntity(String cpf, LocalDate dataNascimento, GeneroEnum genero, String telefone,
                         Set<TiposComidaEnum> preferenciasAlimentares, Set<AlergiaAlimentarEnum> alergias,
                         MetodoPagamentoEnum metodoPagamentoPreferido, Boolean clienteVip,
                         Boolean notificacoesAtivas, String nome, String email, String login, String senha,
                         List<EnderecoEntity> enderecos) {

        super(nome, email, login, senha, enderecos);
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
