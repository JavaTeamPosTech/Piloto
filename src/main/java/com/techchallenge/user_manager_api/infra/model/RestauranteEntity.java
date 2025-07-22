package com.techchallenge.user_manager_api.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "restaurante")
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @OneToOne(mappedBy = "restaurante")
    private EnderecoRestauranteEntity endereco;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "restaurante_tipo_cozinha", joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_cozinha_id")
    )
    private List<TipoCozinhaEntity> tiposCozinha;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "proprietario_id", referencedColumnName = "id")
    private ProprietarioEntity proprietario;

    public RestauranteEntity(String nome, EnderecoRestauranteEntity endereco, List<TipoCozinhaEntity> tiposCozinha,
                             ProprietarioEntity proprietario) {
        this.nome = nome;
        this.endereco = endereco;
        this.tiposCozinha = tiposCozinha;
        this.proprietario = proprietario;
    }

}
