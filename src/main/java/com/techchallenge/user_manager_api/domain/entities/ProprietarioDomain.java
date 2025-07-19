package com.techchallenge.user_manager_api.domain.entities;

import com.techchallenge.user_manager_api.infra.model.enums.StatusContaEnum;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class ProprietarioDomain extends UsuarioDomain {

    private String cnpj;

    private String razaoSocial;

    private String nomeFantasia;

    private String inscricaoEstadual;

    private String telefoneComercial;

    private String whatsapp;

    private StatusContaEnum statusConta;

    public ProprietarioDomain(UUID id, String cnpj, String razaoSocial, String nomeFantasia, String inscricaoEstadual,
                              String telefoneComercial, String whatsapp, StatusContaEnum statusConta, String nome,
                              String email, String login, String senha) {
        super(id, nome, email, login, senha);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.inscricaoEstadual = inscricaoEstadual;
        this.telefoneComercial = telefoneComercial;
        this.whatsapp = whatsapp;
        this.statusConta = statusConta;
    }

}
