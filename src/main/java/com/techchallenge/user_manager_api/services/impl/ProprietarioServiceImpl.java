package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.entities.Proprietario;
import com.techchallenge.user_manager_api.mapper.UsuarioMapper;
import com.techchallenge.user_manager_api.repositories.ProprietarioRepository;
import com.techchallenge.user_manager_api.services.ProprietarioService;
import org.springframework.stereotype.Service;

@Service
public class ProprietarioServiceImpl implements ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    public ProprietarioServiceImpl(ProprietarioRepository proprietarioRepository) {
        this.proprietarioRepository = proprietarioRepository;
    }

    @Override
    public void cadastrarProprietario(ProprietarioRequestDTO proprietarioDTO) {
        Proprietario proprietario = UsuarioMapper.toProprietario(proprietarioDTO);
        proprietarioRepository.save(proprietario);
    }
}
