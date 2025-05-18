package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.ProprietarioResponseDTO;

public interface ProprietarioService {
    void cadastrarProprietario(ProprietarioRequestDTO proprietarioDTO);

    ProprietarioResponseDTO buscarProprietarioPorId(Long id);
}
