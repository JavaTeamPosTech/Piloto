package com.techchallenge.user_manager_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techchallenge.user_manager_api.dto.requests.EnderecoRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "enderecos")
public class Endereco {

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
    private Usuario usuario;

    @OneToOne(mappedBy = "endereco", fetch = FetchType.LAZY)
    private Restaurante restaurante;

    public Endereco(EnderecoRequestDTO dto, Usuario usuario) {
        this.estado = dto.estado();
        this.cidade = dto.cidade();
        this.bairro = dto.bairro();
        this.rua = dto.rua();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.cep = dto.cep();
        this.usuario = usuario;
    }
}
