package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.requests.AtualizarProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.entities.Endereco;
import com.techchallenge.user_manager_api.entities.Proprietario;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.mapper.UsuarioMapper;
import com.techchallenge.user_manager_api.repositories.ProprietarioRepository;
import com.techchallenge.user_manager_api.services.PasswordService;
import com.techchallenge.user_manager_api.services.RestauranteService;
import com.techchallenge.user_manager_api.services.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final ProprietarioRepository proprietarioRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public RestauranteServiceImpl(ProprietarioRepository proprietarioRepository, PasswordService passwordService, TokenService tokenService, UsuarioService usuarioService) {
        this.proprietarioRepository = proprietarioRepository;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    public CadastroResponseDTO cadastrarProprietario(ProprietarioRequestDTO proprietarioDTO) {

        if (usuarioService.existsByLogin(proprietarioDTO.login())) {
            throw new DataIntegrityViolationException("uk_proprietario_login:  O login '" + proprietarioDTO.login() + "' já está em uso.");
        }

        String senhaCriptografada = passwordService.encryptPassword(proprietarioDTO.senha());
        Proprietario proprietario = UsuarioMapper.toProprietario(proprietarioDTO, senhaCriptografada);
        Proprietario proprietarioSalvo = proprietarioRepository.save(proprietario);
        String token = tokenService.generateToken(proprietarioSalvo.getLogin());

        return new CadastroResponseDTO(UsuarioResponseDTO.deProprietario(proprietarioSalvo), token);
    }


}
