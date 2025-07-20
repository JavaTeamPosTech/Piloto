package com.techchallenge.user_manager_api.application.usecases.proprietario;

import com.techchallenge.user_manager_api.api.controllers.gateways.ProprietarioGatewayRepository;
import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarProprietarioRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EditarProprietarioUseCase {
    private final ProprietarioGatewayRepository proprietarioGatewayRepository;
    private final ProprietarioPresenter proprietarioPresenter;

    public EditarProprietarioUseCase(ProprietarioGatewayRepository proprietarioGatewayRepository, ProprietarioPresenter proprietarioPresenter) {
        this.proprietarioGatewayRepository = proprietarioGatewayRepository;
        this.proprietarioPresenter = proprietarioPresenter;
    }

    public ProprietarioResponseDTO executar(UUID id, AtualizarProprietarioRequestDTO proprietarioRequestDTO) {
        ProprietarioDomain proprietarioAtual = proprietarioGatewayRepository.buscarProprietarioPorId(id);

        boolean loginEmUso = proprietarioGatewayRepository.existsByLogin(proprietarioRequestDTO.login());
        if (loginEmUso && !proprietarioAtual.getLogin().equals(proprietarioRequestDTO.login())) {
            throw new IllegalArgumentException("Login já está em uso");
        }

        ProprietarioDomain novoProprietario = UsuarioMapper.toProprietarioDomain(proprietarioRequestDTO, proprietarioAtual.getSenha());
        proprietarioGatewayRepository.atualizar(novoProprietario);
        return proprietarioPresenter.getProprietarioResponseDto(novoProprietario);
    }

}
