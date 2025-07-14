package com.techchallenge.user_manager_api.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techchallenge.user_manager_api.domain.entities.EnderecoDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "enderecos")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name =  "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
    private String cep;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    public EnderecoEntity(EnderecoDomain enderecoDomain, UsuarioEntity usuario) {
        this.estado = enderecoDomain.getEstado();
        this.cidade = enderecoDomain.getCidade();
        this.bairro = enderecoDomain.getBairro();
        this.rua = enderecoDomain.getRua();
        this.numero = enderecoDomain.getNumero();
        this.complemento = enderecoDomain.getComplemento();
        this.cep = enderecoDomain.getCep();
        this.usuario = usuario;
    }
}
