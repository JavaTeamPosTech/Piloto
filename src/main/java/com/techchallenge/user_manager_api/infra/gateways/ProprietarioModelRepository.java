package com.techchallenge.user_manager_api.infra.gateways;

import com.techchallenge.user_manager_api.api.controllers.gateways.ProprietarioGatewayRepository;
import com.techchallenge.user_manager_api.application.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import com.techchallenge.user_manager_api.infra.model.ProprietarioEntity;
import com.techchallenge.user_manager_api.infra.persistence.adapters.UsuarioAdapter;
import com.techchallenge.user_manager_api.infra.repositories.ProprietarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProprietarioModelRepository implements ProprietarioGatewayRepository {

    private final ProprietarioRepository proprietarioRepository;

    public ProprietarioModelRepository(ProprietarioRepository proprietarioRepository) {
        this.proprietarioRepository = proprietarioRepository;
    }

    @Override
    public ProprietarioDomain cadastrarProprietario(ProprietarioDomain proprietarioDomain, String senhaCriptografada) {
        ProprietarioEntity proprietarioEntity = UsuarioAdapter.toProprietario(proprietarioDomain, senhaCriptografada);
        proprietarioRepository.save(proprietarioEntity);
        return UsuarioAdapter.toProprietarioDomain(proprietarioEntity);
    }

    @Override
    public boolean existsByLogin(String login) {
        return false;
    }


    @Override
    public ProprietarioDomain buscarProprietarioPorId(UUID id) {
        return UsuarioAdapter.toProprietarioDomain(verificarProprietarioExiste(id));
    }

    private ProprietarioEntity verificarProprietarioExiste(UUID id) {
        return proprietarioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Proprietário com o id '%s' não encontrado", id)));
    }


    @Override
    public void deletarProprietarioPorId(UUID id) {
        verificarProprietarioExiste(id);
        proprietarioRepository.deleteById(id);
        if (proprietarioRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Falha ao deletar proprietário");
        }
    }
}
