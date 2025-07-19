package com.techchallenge.user_manager_api.application.usecases.presenters;

import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.domain.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class ClientePresenter {

    @Getter
    private UsuarioResponseDTO viewModel;
    private final UsuarioMapper mapper;

    public ClientePresenter(UsuarioMapper mapper) {
        this.mapper = mapper;
    }

    public void apresentar(ClienteDomain usuarioDomain) {
        this.viewModel = mapper.toClienteResponseDto(usuarioDomain);
    }

    public ClienteResponseDTO buscarClientePresenterPorId(ClienteDomain clienteDomain) {
        return mapper.toClienteResponseDTO(clienteDomain);
    }

}
