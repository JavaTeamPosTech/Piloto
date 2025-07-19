package com.techchallenge.user_manager_api.application.usecases.usuario;

import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.domain.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.infra.persistence.adapters.UsuarioAdapter;
import org.springframework.stereotype.Service;

@Service
public class UsuarioPresenter {

    private LoginResponseDTO viewModel;
    private final UsuarioMapper mapper;

    public UsuarioPresenter(UsuarioMapper mapper) {
        this.mapper = mapper;
    }

    public void apresentar(String token) {
        this.viewModel = UsuarioAdapter.toLoginResponseDto(token);
    }

    public LoginResponseDTO getViewModel() {
        return viewModel;
    }

}
