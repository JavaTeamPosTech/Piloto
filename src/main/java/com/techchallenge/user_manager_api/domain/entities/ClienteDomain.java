package com.techchallenge.user_manager_api.domain.entities;

import com.techchallenge.user_manager_api.naousar.entities.Endereco;
import com.techchallenge.user_manager_api.naousar.entities.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.naousar.entities.enums.GeneroEnum;
import com.techchallenge.user_manager_api.naousar.entities.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.naousar.entities.enums.TiposComidaEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public ClienteDomain(String cpf, LocalDate dataNascimento, GeneroEnum genero, String telefone,
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
