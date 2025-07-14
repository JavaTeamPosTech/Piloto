package com.techchallenge.user_manager_api.application.usecases;

import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.domain.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import org.springframework.stereotype.Service;

@Service
public class ClientePresenter {

    private UsuarioResponseDTO viewModel;
    private final UsuarioMapper mapper;

    public ClientePresenter(UsuarioMapper mapper) {
        this.mapper = mapper;
    }

    public void apresentar(ClienteDomain usuarioDomain) {
        this.viewModel = mapper.toResponseDto(usuarioDomain);
    }

    public UsuarioResponseDTO getViewModel() {
        return viewModel;
    }

}
