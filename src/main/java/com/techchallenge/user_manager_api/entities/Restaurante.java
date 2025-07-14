package com.techchallenge.user_manager_api.entities;

import com.techchallenge.user_manager_api.entities.enums.TipoCozinhaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "restaurantes")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: Para o controle de sizes poderíamos usar constantes
    @Column(nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cozinha", nullable = false, length = 50)
    private TipoCozinhaEnum tipoCozinha;

    // TODO: Horário como string (ideal evoluir para VO)
    @Column(name = "horario_funcionamento", nullable = false, length = 50)
    private String horarioFuncionamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Proprietario proprietario;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

}
