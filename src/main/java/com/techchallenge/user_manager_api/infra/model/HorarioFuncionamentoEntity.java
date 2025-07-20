package com.techchallenge.user_manager_api.infra.model;

import com.techchallenge.user_manager_api.infra.model.enums.DiaSemanaEnum;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "horario_funcionamento")
public class HorarioFuncionamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private DiaSemanaEnum diaSemana;

    @Column(name = "hora_abertura")
    private LocalTime horaAbertura;

    @JoinColumn(name = "hora_fechamento")
    private LocalTime horaFechamento;

    @ManyToOne
    private RestauranteEntity restaurante;
}
