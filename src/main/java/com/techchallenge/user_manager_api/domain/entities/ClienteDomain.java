package com.techchallenge.user_manager_api.domain.entities;

import com.techchallenge.user_manager_api.application.inputs.AtualizarClienteInput;
import com.techchallenge.user_manager_api.application.inputs.EnderecoInput;
import com.techchallenge.user_manager_api.infra.model.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.infra.model.enums.GeneroEnum;
import com.techchallenge.user_manager_api.infra.model.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.infra.model.enums.TiposComidaEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
                         MetodoPagamentoEnum metodoPagamentoPreferido, Boolean notificacoesAtivas,
                         Boolean clienteVip, Integer saldoPontos, Integer avaliacoesFeitas, Date ultimoPedido,
                         String nome, String email, String login, String senhaCriptografada) {

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
        this.saldoPontos = saldoPontos;
        this.avaliacoesFeitas = avaliacoesFeitas;
        this.ultimoPedido = ultimoPedido;
    }

    public ClienteDomain(UUID id,
                         String cpf,
                         LocalDate dataNascimento,
                         GeneroEnum genero,
                         String telefone,
                         Set<TiposComidaEnum> preferenciasAlimentares,
                         Set<AlergiaAlimentarEnum> alergias,
                         MetodoPagamentoEnum metodoPagamentoPreferido,
                         Boolean notificacoesAtivas,
                         String nome,
                         String email,
                         String login,
                         String senhaCriptografada) {

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
        this.notificacoesAtivas = notificacoesAtivas;

        this.clienteVip = false;
        this.saldoPontos = 0;
        this.avaliacoesFeitas = 0;
        this.ultimoPedido = null;
    }


      public ClienteDomain atualizarCom(AtualizarClienteInput input, String senhaCriptografada) {
        if (input.getCpf() != null) {
            this.cpf = input.getCpf();
        }
        if (input.getDataNascimento() != null) {
            this.dataNascimento = input.getDataNascimento();
        }
        if (input.getGenero() != null) {
            this.genero = input.getGenero();
        }
        if (input.getTelefone() != null) {
            this.telefone = input.getTelefone();
        }
        if (input.getPreferenciasAlimentares() != null) {
            this.preferenciasAlimentares = input.getPreferenciasAlimentares();
        }
        if (input.getAlergias() != null) {
            this.alergias = input.getAlergias();
        }
        if (input.getMetodoPagamentoPreferido() != null) {
            this.metodoPagamentoPreferido = input.getMetodoPagamentoPreferido();
        }
        if (input.getNotificacoesAtivas() != null) {
            this.notificacoesAtivas = input.getNotificacoesAtivas();
        }

        if (input.getNome() != null) {
            this.setNome(input.getNome());
        }
        if (input.getEmail() != null) {
            this.setEmail(input.getEmail());
        }
        if (input.getLogin() != null) {
            this.setLogin(input.getLogin());
        }
        if (senhaCriptografada != null) {
            this.setSenha(senhaCriptografada);
        }

        atualizarEnderecos(input.getEnderecos());

        return this;
    }

    private void atualizarEnderecos(List<EnderecoInput> listaAtualDeEnderecos) {
        if (listaAtualDeEnderecos == null) return;

        // Mapeia os endereços atuais por ID para facilitar a busca
        Map<UUID, EnderecoDomain> enderecosAtuaisMap = this.getEnderecos().stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(EnderecoDomain::getId, e -> e));

        // Lista para armazenar os endereços atualizados/finais
        List<EnderecoDomain> enderecosAtualizados = new ArrayList<>();

        for (EnderecoInput input : listaAtualDeEnderecos) {
            if (input.getId() != null) {
                // Endereço já existe, atualizar os campos
                EnderecoDomain enderecoExistente = enderecosAtuaisMap.get(input.getId());
                if (enderecoExistente != null) {
                    enderecoExistente.atualizarCom(input);
                    enderecosAtualizados.add(enderecoExistente);
                    // Remove do mapa para controlar o que sobrou
                    enderecosAtuaisMap.remove(input.getId());
                } else {
                    // ID informado mas não encontrado nos atuais, considera como novo
                    EnderecoDomain novoEndereco = new EnderecoDomain(input, this);
                    enderecosAtualizados.add(novoEndereco);
                }
            } else {
                // Sem ID: novo endereço
                EnderecoDomain novoEndereco = new EnderecoDomain(input, this);
                enderecosAtualizados.add(novoEndereco);
            }
        }

        // O que sobrou no mapa de endereços atuais são endereços removidos, então não adicionamos

        // Finalmente substitui a lista original pelos atualizados
        this.getEnderecos().clear();
        this.getEnderecos().addAll(enderecosAtualizados);
    }





}
