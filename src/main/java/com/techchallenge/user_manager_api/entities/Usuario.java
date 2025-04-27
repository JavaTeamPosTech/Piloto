package com.techchallenge.user_manager_api.entities;

import com.techchallenge.user_manager_api.dto.AtualizarUsuarioRequestDTO;
import com.techchallenge.user_manager_api.dto.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.UsuarioRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "usuarios")
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    @Column(name = "ultima_alteracao")
    private LocalDate ultimaAlteracao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();


//    public Usuario(UsuarioRequestDTO dto) {
//        this.nome = dto.nome();
//        this.email = dto.email();
//        this.login = dto.login();
//        this.senha = dto.senha();
//        this.ultimaAlteracao = LocalDate.now();
//
//        for (EnderecoRequestDTO endereco : dto.enderecos()) {
//            this.enderecos.add(new Endereco(endereco, this));
//        }
//
//    }
//
//    public void alterarInformacoes(AtualizarUsuarioRequestDTO dto) {
//            this.nome = dto.nome();
//            this.email = dto.email();
//            //this.endereco = dto.endereco();
//    }
}
