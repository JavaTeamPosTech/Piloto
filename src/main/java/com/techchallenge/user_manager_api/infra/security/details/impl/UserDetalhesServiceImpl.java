package com.techchallenge.user_manager_api.infra.security.details.impl;

import com.techchallenge.user_manager_api.infra.security.details.UserDetalhesService;
import com.techchallenge.user_manager_api.naousar.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetalhesServiceImpl implements UserDetalhesService {

    private final UsuarioRepository usuarioRepository;

    public UserDetalhesServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);
    }
}
