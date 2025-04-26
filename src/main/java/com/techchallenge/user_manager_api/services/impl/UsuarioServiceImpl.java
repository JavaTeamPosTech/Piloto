package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.dto.AtualizarUsuarioRequestDTO;
import com.techchallenge.user_manager_api.dto.UsuarioDTO;
import com.techchallenge.user_manager_api.entities.Usuario;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.repositories.UsuarioRepository;
import com.techchallenge.user_manager_api.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public void cadastrarUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario(dto);
        usuarioRepository.save(usuario);
    }

    @Override
    public void removerUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        usuarioRepository.deleteById(usuario.getId());
    }

    @Override
    public void alterarUsuario(AtualizarUsuarioRequestDTO dto, Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        usuario.alterarInformacoes(dto);
        usuarioRepository.save(usuario);
    }


}
