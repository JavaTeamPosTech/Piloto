package com.techchallenge.user_manager_api.entities;

import com.techchallenge.user_manager_api.entities.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "clientes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cliente_cpf", columnNames = "cpf"),
                @UniqueConstraint(name = "uk_cliente_telefone", columnNames = "telefone")
        })
public class Cliente extends Usuario {

    @Setter
    private String cpf;

    @Setter
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Setter
    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;

    @Setter
    private String telefone;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "preferencias_alimentares")
    private Set<TiposComidaEnum> preferenciasAlimentares = new HashSet<>();

    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AlergiaAlimentarEnum> alergias = new HashSet<>();

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento_preferido")
    private MetodoPagamentoEnum metodoPagamentoPreferido;

    @Setter
    private Date ultimoPedido;

    @Setter
    @Column(name = "saldo_pontos")
    private Integer saldoPontos;

    @Setter
    @Column(name = "cliente_vip")
    private Boolean clienteVip;

    @Setter
    @Column(name = "avaliacoes_feitas")
    private Integer avaliacoesFeitas;

    @Setter
    @Column(name = "notificacoes_ativas")
    private Boolean notificacoesAtivas;

    public Cliente(String cpf, LocalDate dataNascimento, GeneroEnum genero, String telefone,
                   Set<TiposComidaEnum> preferenciasAlimentares, Set<AlergiaAlimentarEnum> alergias,
                   MetodoPagamentoEnum metodoPagamentoPreferido, Boolean clienteVip,
                   Boolean notificacoesAtivas, String nome, String email, String login, String senha,
                   List<Endereco> enderecos) {

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
