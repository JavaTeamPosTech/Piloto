package com.techchallenge.user_manager_api.infra.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "restaurante")
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoRestauranteEntity> enderecos;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "restaurante_tipo_cozinha", joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_cozinha_id")
    )
    private Set<TipoCozinhaEntity> tiposCozinha;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "proprietario_id", referencedColumnName = "id")
    private ProprietarioEntity proprietario;
}
