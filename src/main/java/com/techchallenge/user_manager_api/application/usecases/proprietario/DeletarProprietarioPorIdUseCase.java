package com.techchallenge.user_manager_api.application.usecases.proprietario;

import com.techchallenge.user_manager_api.api.controllers.gateways.ProprietarioGatewayRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarProprietarioPorIdUseCase {
    private final ProprietarioGatewayRepository  proprietarioRepository;
    private final ProprietarioPresenter proprietarioPresenter;

    public DeletarProprietarioPorIdUseCase(ProprietarioGatewayRepository  proprietarioRepository, ProprietarioPresenter proprietarioPresenter){
        this.proprietarioRepository = proprietarioRepository;
        this.proprietarioPresenter = proprietarioPresenter;
    }


    public void executar(UUID id){
        proprietarioRepository.deletarProprietarioPorId(id);
    }
}
