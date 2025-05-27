package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.Proprietario;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.mapper.UsuarioMapper;
import com.techchallenge.user_manager_api.repositories.ProprietarioRepository;
import com.techchallenge.user_manager_api.services.PasswordService;
import com.techchallenge.user_manager_api.services.ProprietarioService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProprietarioServiceImpl implements ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    public ProprietarioServiceImpl(ProprietarioRepository proprietarioRepository, PasswordService passwordService, TokenService tokenService) {
        this.proprietarioRepository = proprietarioRepository;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
    }

    @Override
    public CadastroResponseDTO cadastrarProprietario(ProprietarioRequestDTO proprietarioDTO) {
        String senhaCriptografada = passwordService.encryptPassword(proprietarioDTO.senha());
        Proprietario proprietario = UsuarioMapper.toProprietario(proprietarioDTO, senhaCriptografada);
        Proprietario proprietarioSalvo = proprietarioRepository.save(proprietario);
        String token = tokenService.generateToken(proprietarioSalvo.getLogin());

        return new CadastroResponseDTO(UsuarioResponseDTO.deProprietario(proprietarioSalvo), token);
    }

    @Override
    public ProprietarioResponseDTO buscarProprietarioPorId(UUID id) {
        Proprietario proprietario = proprietarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proprietario não encontrado"));
        return UsuarioMapper.toProprietarioResponseDTO(proprietario);
    }
}
