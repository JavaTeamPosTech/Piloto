package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.entities.Proprietario;
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
    public void criarProprietario(ProprietarioRequestDTO proprietarioDTO) {
        Proprietario proprietario = new Proprietario();
        proprietarioRepository.save(proprietario);
    }
}
