package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.UsuarioGatewayRepository;
import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import com.techchallenge.user_manager_api.infra.model.UsuarioEntity;
import com.techchallenge.user_manager_api.infra.persistence.adapters.UsuarioAdapter;
import com.techchallenge.user_manager_api.infra.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioModelRepository implements UsuarioGatewayRepository {

    private final UsuarioRepository usuarioRepository;

    public UsuarioModelRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean existsByLogin(String login) {
        return usuarioRepository.existsByLogin(login);
    }

    @Override
    public UsuarioDomain findByLogin(String login) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(login);
        return UsuarioAdapter.toUsuarioDomain(usuarioEntity);
    }

    @Override
    public void atualizarSenha(String login, String senhaCriptografada) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(login);
        if (usuarioEntity != null) {
            usuarioEntity.atualizarSenha(senhaCriptografada);
            usuarioRepository.save(usuarioEntity);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
    }
}
