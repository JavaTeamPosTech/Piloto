package com.techchallenge.user_manager_api.infra.model;

import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.domain.entities.EnderecoDomain;
import com.techchallenge.user_manager_api.infra.model.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.infra.model.enums.GeneroEnum;
import com.techchallenge.user_manager_api.infra.model.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.infra.model.enums.TiposComidaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public ClienteEntity atualizarCom(ClienteDomain clienteDomain, String senhaCriptografada) {
        if (clienteDomain.getNome() != null) {
            this.setNome(clienteDomain.getNome());
        }
        if (clienteDomain.getEmail() != null) {
            this.setEmail(clienteDomain.getEmail());
        }
        if (clienteDomain.getLogin() != null) {
            this.setLogin(clienteDomain.getLogin());
        }
        if (senhaCriptografada != null) {
            this.atualizarSenha(senhaCriptografada);
        }
        if (clienteDomain.getCpf() != null) {
            this.cpf = clienteDomain.getCpf();
        }
        if (clienteDomain.getDataNascimento() != null) {
            this.dataNascimento = clienteDomain.getDataNascimento();
        }
        if (clienteDomain.getGenero() != null) {
            this.genero = clienteDomain.getGenero();
        }
        if (clienteDomain.getTelefone() != null) {
            this.telefone = clienteDomain.getTelefone();
        }
        if (clienteDomain.getPreferenciasAlimentares() != null) {
            this.preferenciasAlimentares = clienteDomain.getPreferenciasAlimentares();
        }
        if (clienteDomain.getAlergias() != null) {
            this.alergias = clienteDomain.getAlergias();
        }
        if (clienteDomain.getMetodoPagamentoPreferido() != null) {
            this.metodoPagamentoPreferido = clienteDomain.getMetodoPagamentoPreferido();
        }
        if (clienteDomain.getNotificacoesAtivas() != null) {
            this.notificacoesAtivas = clienteDomain.getNotificacoesAtivas();
        }

        if (clienteDomain.getEnderecos() != null) {
            this.atualizarEnderecos(clienteDomain);
        }

        return this;
    }

    public void atualizarEnderecos(ClienteDomain clienteDomain) {
        List<EnderecoDomain> enderecosDomain = clienteDomain.getEnderecos();
        if (enderecosDomain == null) return;

        if (this.getEnderecos() == null) {
            this.setEnderecos(new ArrayList<>());
        }

        Map<UUID, EnderecoEntity> enderecosAtuaisMap = this.getEnderecos().stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(EnderecoEntity::getId, e -> e));

        // Limpa a lista original para manter a mesma instância
        this.getEnderecos().clear();

        for (EnderecoDomain enderecoDomain : enderecosDomain) {
            if (enderecoDomain.getId() != null && enderecosAtuaisMap.containsKey(enderecoDomain.getId())) {
                EnderecoEntity enderecoExistente = enderecosAtuaisMap.get(enderecoDomain.getId());
                // Atualiza campos da entidade usando dados do domínio já validados
                enderecoExistente.atualizarCom(enderecoDomain);
                this.getEnderecos().add(enderecoExistente);
                enderecosAtuaisMap.remove(enderecoDomain.getId());
            } else {
                EnderecoEntity novoEndereco = new EnderecoEntity(enderecoDomain, this);
                this.getEnderecos().add(novoEndereco);
            }
        }

        // Os endereços que sobraram em enderecosAtuaisMap foram removidos no domínio
        // Como a coleção original foi limpa e reenchida, eles não estarão mais presentes e o orphanRemoval cuidará da exclusão
    }













}
