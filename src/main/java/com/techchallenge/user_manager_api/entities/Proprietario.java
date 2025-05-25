package com.techchallenge.user_manager_api.entities;

import com.techchallenge.user_manager_api.entities.enums.RolesEnum;
import com.techchallenge.user_manager_api.entities.enums.StatusContaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "proprietarios")
public class Proprietario extends Usuario {

    private String cnpj;
    @Column(name = "razao_social")
    private String razaoSocial;
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;
    @Column(name = "telefone_comercial")
    private String telefoneComercial;
    private String whatsapp;
    @Column(name = "status_conta")
    private StatusContaEnum statusConta;

    public Proprietario(String cnpj, String razaoSocial, String nomeFantasia, String inscricaoEstadual,
                        String telefoneComercial, String whatsapp, StatusContaEnum statusConta, String nome,
                        String email, String login, String senha, List<Endereco> enderecos) {
        super(nome, email, login, senha, enderecos, RolesEnum.PROPRIETARIO);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.inscricaoEstadual = inscricaoEstadual;
        this.telefoneComercial = telefoneComercial;
        this.whatsapp = whatsapp;
        this.statusConta = statusConta;
    }
}
