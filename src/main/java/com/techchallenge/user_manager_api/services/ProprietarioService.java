package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.ProprietarioRequestDTO;

public interface ProprietarioService {
    void cadastrarProprietario(ProprietarioRequestDTO proprietarioDTO);
}
