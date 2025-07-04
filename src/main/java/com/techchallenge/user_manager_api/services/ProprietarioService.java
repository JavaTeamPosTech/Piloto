package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.requests.AtualizarProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ProprietarioResponseDTO;

import java.util.UUID;

public interface ProprietarioService {
    CadastroResponseDTO cadastrarProprietario(ProprietarioRequestDTO proprietarioDTO);
    ProprietarioResponseDTO buscarProprietarioPorId(UUID id);
    void deletarProprietario(UUID id);
    ProprietarioResponseDTO editarProprietario(UUID id, AtualizarProprietarioRequestDTO proprietarioRequestDTO);
}
