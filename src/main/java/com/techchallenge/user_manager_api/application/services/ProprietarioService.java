package com.techchallenge.user_manager_api.application.services;


import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarProprietarioRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ProprietarioResponseDTO;

import java.util.UUID;

public interface ProprietarioService {
    CadastroResponseDTO cadastrarProprietario(ProprietarioRequestDTO proprietarioDTO);
    ProprietarioResponseDTO buscarProprietarioPorId(UUID id);
    void deletarProprietario(UUID id);
    ProprietarioResponseDTO editarProprietario(UUID id, AtualizarProprietarioRequestDTO proprietarioRequestDTO);
}
