package com.techchallenge.user_manager_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "proprietarios")
public class Proprietario extends Usuario {

    public Proprietario(String nome, String email, String login, String senha, List<Endereco> enderecos) {
        super(nome, email, login, senha, enderecos);
    }
}
