package com.techchallenge.user_manager_api.application.mappers;

import com.techchallenge.user_manager_api.domain.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.EnderecoResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.UsuarioResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.domain.entities.EnderecoDomain;
import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
                senhaCriptografada
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


    public static UsuarioResponseDTO toResponseDto(ClienteDomain clienteDomain) {
        return new UsuarioResponseDTO(
                clienteDomain.getId(),
                clienteDomain.getNome(),
                clienteDomain.getEmail(),
                clienteDomain.getLogin()
        );
    }

    public static ClienteResponseDTO toClienteResponseDTO(ClienteDomain clienteDomain) {
        return new ClienteResponseDTO(clienteDomain.getId(), clienteDomain.getNome(), clienteDomain.getCpf(),
                clienteDomain.getDataNascimento(), clienteDomain.getEmail(), clienteDomain.getLogin(), clienteDomain.getTelefone(),
                toEnderecoResponseDTO(clienteDomain.getEnderecos())
        );
    }

    private static List<EnderecoResponseDTO> toEnderecoResponseDTO(List<EnderecoDomain> enderecoDomain) {
        List<EnderecoResponseDTO> enderecosResponseDTO = new ArrayList<>();

        for (EnderecoDomain endereco : enderecoDomain) {
            EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO(endereco.getId(), endereco.getEstado(),
                    endereco.getCidade(), endereco.getBairro(), endereco.getRua(), endereco.getNumero(), endereco.getComplemento(),
                    endereco.getCep());
            enderecosResponseDTO.add(enderecoResponseDTO);

        }
        return enderecosResponseDTO;
    }




//    private static EnderecoResponseDTO toEnderecoResponseDto(List<EnderecoDomain> enderecosDomain) {
//
//        if (enderecosDomain == null || enderecosDomain.isEmpty()) return;
//
//        List<EnderecoDomain> enderecos = dtos.stream()
//                .map(enderecoDTO -> new EnderecoDomain(enderecoDTO, usuarioDomain))
//                .toList();
//
//        usuarioDomain.getEnderecos().addAll(enderecos);
//
//        return new EnderecoResponseDTO(
//                enderecoDomain.getId(),
//                enderecoDomain.getEstado(),
//                enderecoDomain.getCidade(),
//                enderecoDomain.getBairro(),
//                enderecoDomain.getRua(),
//                enderecoDomain.getNumero(),
//                enderecoDomain.getComplemento(),
//                enderecoDomain.getCep()
//        );
//    }

}
