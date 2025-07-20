package com.techchallenge.user_manager_api.application.usecases.proprietario;

import com.techchallenge.user_manager_api.api.controllers.gateways.ProprietarioGatewayRepository;
import com.techchallenge.user_manager_api.domain.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import com.techchallenge.user_manager_api.infra.persistence.adapters.UsuarioAdapter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarProprietarioPorIdUseCase {
    private final ProprietarioGatewayRepository proprietarioRepository;
    private final ProprietarioPresenter proprietarioPresenter;

    public BuscarProprietarioPorIdUseCase(ProprietarioGatewayRepository proprietarioRepository,
                                          ProprietarioPresenter proprietarioPresenter){
        this.proprietarioRepository = proprietarioRepository;
        this.proprietarioPresenter = proprietarioPresenter;
    }

    public ProprietarioResponseDTO executar(UUID id){
        ProprietarioDomain domain = proprietarioRepository.buscarProprietarioPorId(id);
        return proprietarioPresenter.getProprietarioResponseDto(domain);
        //return UsuarioAdapter.toProprietarioResponseDTO(domain);
    }
}
