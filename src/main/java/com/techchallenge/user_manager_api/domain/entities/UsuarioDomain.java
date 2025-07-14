package com.techchallenge.user_manager_api.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UsuarioDomain {

    private UUID id;

    private String nome;

    private String email;

    private String login;

    private String senha;

    private LocalDateTime dataAtualizacao;

    private LocalDateTime dataCriacao;

    private List<EnderecoDomain> enderecos;

    public UsuarioDomain(String nome, String email, String login, String senha) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.enderecos = new ArrayList<>();;
    }

}
