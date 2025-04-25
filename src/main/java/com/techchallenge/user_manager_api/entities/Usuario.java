package com.techchallenge.user_manager_api.entities;

import com.techchallenge.user_manager_api.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Getter
@Entity(name = "Usuario")
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    @Column(name = "ultima_alteracao")
    private LocalDate ultimaAlteracao;
    private String endereco;


    public Usuario(UsuarioDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.login = dto.login();
        this.senha = dto.senha();
        this.ultimaAlteracao = LocalDate.now();
        this.endereco = dto.endereco();
    }

}
