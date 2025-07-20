package com.techchallenge.user_manager_api.domain.entities;

import com.techchallenge.user_manager_api.infra.model.enums.DiaSemanaEnum;

import java.time.LocalTime;
import java.util.UUID;

public class HorarioFuncionamentoDomain {

    private UUID id;

    private DiaSemanaEnum diaSemana;


    private LocalTime horaAbertura;


    private LocalTime horaFechamento;


    private RestauranteDomain restaurante;
}
