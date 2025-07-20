package com.techchallenge.user_manager_api.application.usecases.proprietario;

import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.domain.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import org.springframework.stereotype.Service;

@Service
public class ProprietarioPresenter {

    private UsuarioResponseDTO viewModel;
    private final UsuarioMapper mapper;

    public ProprietarioPresenter(UsuarioMapper mapper) {
        this.mapper = mapper;
    }

    public void apresentar(ProprietarioDomain proprietarioDomain) {
        this.viewModel = mapper.toProprietarioResponseDto(proprietarioDomain);
    }

    public UsuarioResponseDTO getViewModel() {
        return viewModel;
    }

    public ProprietarioResponseDTO getProprietarioResponseDto(ProprietarioDomain proprietarioDomain) {
        return UsuarioMapper.toProprietarioResponseDtoComEndereco(proprietarioDomain);
    }

}
