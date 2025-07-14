package com.techchallenge.user_manager_api.application.mappers;

import com.techchallenge.user_manager_api.domain.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.domain.entities.EnderecoDomain;
import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import com.techchallenge.user_manager_api.infra.model.ClienteEntity;
import com.techchallenge.user_manager_api.naousar.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.naousar.entities.Endereco;
import com.techchallenge.user_manager_api.naousar.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {

    public static ClienteDomain toClienteDomain(ClienteRequestDTO dto, String senhaCriptografada) {

            ClienteDomain clienteDomain = new ClienteDomain(
                    dto.cpf(),
                    dto.dataNascimento(),
                    dto.genero(),
                    dto.telefone(),
                    dto.preferenciasAlimentares(),
                    dto.alergias(),
                    dto.metodoPagamentoPreferido(),
                    dto.clienteVip(),
                    dto.notificacoesAtivas(),
                    dto.nome(),
                    dto.email(),
                    dto.login(),
                    senhaCriptografada,
                    new ArrayList<>()
            );

            adicionarEnderecosAoUsuario(clienteDomain, dto.enderecos());
            return clienteDomain;
    }

    private static void adicionarEnderecosAoUsuario(UsuarioDomain usuarioDomain, List<EnderecoRequestDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) return;

        List<EnderecoDomain> enderecos = dtos.stream()
                .map(enderecoDTO -> new EnderecoDomain(enderecoDTO, usuarioDomain))
                .toList();

        usuarioDomain.getEnderecos().addAll(enderecos);
    }

}
