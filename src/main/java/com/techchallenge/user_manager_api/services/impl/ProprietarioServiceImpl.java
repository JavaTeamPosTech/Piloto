package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.entities.Proprietario;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.mapper.UsuarioMapper;
import com.techchallenge.user_manager_api.repositories.ProprietarioRepository;
import com.techchallenge.user_manager_api.services.PasswordService;
import com.techchallenge.user_manager_api.services.ProprietarioService;
import org.springframework.stereotype.Service;

@Service
public class ProprietarioServiceImpl implements ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;
    private final PasswordService passwordService;

    public ProprietarioServiceImpl(ProprietarioRepository proprietarioRepository, PasswordService passwordService) {
        this.proprietarioRepository = proprietarioRepository;
        this.passwordService = passwordService;
    }

    @Override
    public void cadastrarProprietario(ProprietarioRequestDTO proprietarioDTO) {
        String senhaCriptografada = passwordService.encryptPassword(proprietarioDTO.senha());
        Proprietario proprietario = UsuarioMapper.toProprietario(proprietarioDTO, senhaCriptografada);
        proprietarioRepository.save(proprietario);
    }

    @Override
    public ProprietarioResponseDTO buscarProprietarioPorId(Long id) {
        Proprietario proprietario = proprietarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proprietario não encontrado"));
        return UsuarioMapper.toProprietarioResponseDTO(proprietario);
    }
}
