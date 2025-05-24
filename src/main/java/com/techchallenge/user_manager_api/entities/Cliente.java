package com.techchallenge.user_manager_api.entities;

import com.techchallenge.user_manager_api.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.entities.enums.TiposComidaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    private String cpf;
    @Column(name = "data_nascimento")
    private Date dataNascimento;
    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;
    private String telefone;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "preferencias_alimentares")
    private Set<TiposComidaEnum> preferenciasAlimentares;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AlergiaAlimentarEnum> alergias;
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento_preferido")
    private MetodoPagamentoEnum metodoPagamentoPreferido;
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;
    private Date ultimoPedido;
    @Column(name = "saldo_pontos")
    private Integer saldoPontos;
    @Column(name = "cliente_vip")
    private Boolean clienteVip;
    @Column(name = "avaliacoes_feitas")
    private Integer avaliacoesFeitas;
    @Column(name = "notificacoes_ativas")
    private Boolean notificacoesAtivas;


    public Cliente(String cpf, Date dataNascimento, GeneroEnum genero, String telefone,
                   Set<TiposComidaEnum> preferenciasAlimentares, Set<AlergiaAlimentarEnum> alergias,
                   MetodoPagamentoEnum metodoPagamentoPreferido, Boolean clienteVip,
                   Boolean notificacoesAtivas, String nome, String email, String login, String senha,
                   List<Endereco> enderecos) {

        super(nome, email, login, senha, enderecos);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
        this.preferenciasAlimentares = preferenciasAlimentares;
        this.alergias = alergias;
        this.metodoPagamentoPreferido = metodoPagamentoPreferido;
        this.clienteVip = clienteVip;
        this.notificacoesAtivas = notificacoesAtivas;
        this.dataCadastro = LocalDateTime.now();
    }

}
