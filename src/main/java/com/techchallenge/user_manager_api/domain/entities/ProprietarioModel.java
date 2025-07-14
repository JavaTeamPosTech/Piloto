package com.techchallenge.user_manager_api.domain.entities;

import com.techchallenge.user_manager_api.naousar.entities.Endereco;
import com.techchallenge.user_manager_api.naousar.entities.enums.StatusContaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

public class ProprietarioModel extends UsuarioDomain {

    private String cnpj;

    private String razaoSocial;

    private String nomeFantasia;

    private String inscricaoEstadual;

    private String telefoneComercial;

    private String whatsapp;

    private StatusContaEnum statusConta;

    public ProprietarioModel(String cnpj, String razaoSocial, String nomeFantasia, String inscricaoEstadual,
                              String telefoneComercial, String whatsapp, StatusContaEnum statusConta, String nome,
                              String email, String login, String senha, List<Endereco> enderecos) {
        super(nome, email, login, senha, enderecos);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.inscricaoEstadual = inscricaoEstadual;
        this.telefoneComercial = telefoneComercial;
        this.whatsapp = whatsapp;
        this.statusConta = statusConta;
    }

}
