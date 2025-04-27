package com.techchallenge.user_manager_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    public Cliente(String nome, String email, String login, String senha, List<Endereco> enderecos) {
        super(nome, email, login, senha, enderecos);
    }

}
