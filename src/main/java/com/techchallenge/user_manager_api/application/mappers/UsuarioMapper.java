package com.techchallenge.user_manager_api.application.mappers;

import com.techchallenge.user_manager_api.domain.dto.requests.*;
import com.techchallenge.user_manager_api.domain.dto.response.*;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.domain.entities.EnderecoDomain;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UsuarioMapper {

    public static ClienteDomain toClienteDomain(ClienteRequestDTO dto, String senhaCriptografada) {

        ClienteDomain clienteDomain = new ClienteDomain(
                null,
                dto.cpf(),
                dto.dataNascimento(),
                dto.genero(),
                dto.telefone(),
                dto.preferenciasAlimentares(),
                dto.alergias(),
                dto.metodoPagamentoPreferido(),
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

    public static UsuarioResponseDTO toClienteResponseDto(ClienteDomain clienteDomain) {
        return new UsuarioResponseDTO(
                clienteDomain.getId(),
                clienteDomain.getNome(),
                clienteDomain.getEmail(),
                clienteDomain.getLogin()
        );
    }

    public static UsuarioResponseDTO toProprietarioResponseDto(ProprietarioDomain proprietarioDomain) {
        return new UsuarioResponseDTO(
                proprietarioDomain.getId(),
                proprietarioDomain.getNome(),
                proprietarioDomain.getEmail(),
                proprietarioDomain.getLogin()
        );
    }

    public static ProprietarioDomain toProprietarioDomain(ProprietarioRequestDTO dto, String senhaCriptografada) {

        ProprietarioDomain proprietarioDomain = new ProprietarioDomain(
                null,
                dto.cnpj(),
                dto.razaoSocial(),
                dto.nomeFantasia(),
                dto.inscricaoEstadual(),
                dto.telefoneComercial(),
                dto.whatsapp(),
                dto.statusConta(),
                dto.nome(),
                dto.email(),
                dto.login(),
                senhaCriptografada
        );

        adicionarEnderecosAoUsuario(proprietarioDomain, dto.enderecos());
        return proprietarioDomain;
    }

    public static LoginResponseDTO toLoginResponseDto(String token){
        return new LoginResponseDTO(
            token
        );
    }

//    public static UsuarioDomain toUsuarioDomain(LoginRequestDTO dto){
//        return new UsuarioDomain(
//                null,
//                dto.nome(),
//                dto.email(),
//                dto.login(),
//                dto.senha()
//        );
//    }

    public static ClienteResponseDTO toClienteResponseDTO(ClienteDomain clienteDomain) {
        return new ClienteResponseDTO(clienteDomain.getId(), clienteDomain.getNome(), clienteDomain.getCpf(),
                clienteDomain.getDataNascimento(), clienteDomain.getEmail(), clienteDomain.getLogin(), clienteDomain.getTelefone(),
                toEnderecoResponseDTO(clienteDomain.getEnderecos())
        );
    }

    public static List<EnderecoResponseDTO> toEnderecoResponseDTO(List<EnderecoDomain> enderecoDomain) {
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

    public static ClienteDomain toClienteDomain(AtualizarClienteRequestDTO dto, String senhaCriptografada) {

        ClienteDomain clienteDomain = new ClienteDomain(
                null,
                dto.cpf(),
                dto.dataNascimento(),
                dto.genero(),
                dto.telefone(),
                dto.preferenciasAlimentares(),
                dto.alergias(),
                dto.metodoPagamentoPreferido(),
                dto.notificacoesAtivas(),
                dto.nome(),
                dto.email(),
                dto.login(),
                senhaCriptografada
        );

        adicionarEnderecosAoUsuario(clienteDomain, dto.enderecos());
        return clienteDomain;
    }

    public static ProprietarioResponseDTO toProprietarioResponseDtoComEndereco(ProprietarioDomain proprietarioDomain) {
        return new ProprietarioResponseDTO(
                proprietarioDomain.getId(),
                proprietarioDomain.getCnpj(),
                proprietarioDomain.getRazaoSocial(),
                proprietarioDomain.getNomeFantasia(),
                proprietarioDomain.getInscricaoEstadual(),
                proprietarioDomain.getTelefoneComercial(),
                proprietarioDomain.getWhatsapp(),
                proprietarioDomain.getNome(),
                proprietarioDomain.getEmail(),
                proprietarioDomain.getLogin(),
                toEnderecoResponseDto(proprietarioDomain.getEnderecos())
        );
    }

    private static List<EnderecoResponseDTO> toEnderecoResponseDto(List<EnderecoDomain> enderecosDomain) {
        if (enderecosDomain == null || enderecosDomain.isEmpty()) {
            return Collections.emptyList();
        }

        return enderecosDomain.stream()
                .map(enderecoDomain -> new EnderecoResponseDTO(
                        enderecoDomain.getId(),
                        enderecoDomain.getEstado(),
                        enderecoDomain.getCidade(),
                        enderecoDomain.getBairro(),
                        enderecoDomain.getRua(),
                        enderecoDomain.getNumero(),
                        enderecoDomain.getComplemento(),
                        enderecoDomain.getCep()
                ))
                .toList();
    }
    public static ProprietarioDomain toProprietarioDomain(AtualizarProprietarioRequestDTO dto, String senhaCriptografada) {

        ProprietarioDomain proprietarioDomain = new ProprietarioDomain(
                null,
                dto.cnpj(),
                dto.razaoSocial(),
                dto.nomeFantasia(),
                dto.inscricaoEstadual(),
                dto.telefoneComercial(),
                dto.whatsapp(),
                dto.statusConta(),
                dto.nome(),
                dto.email(),
                dto.login(),
                senhaCriptografada
        );

        adicionarEnderecosAoUsuario(proprietarioDomain, dto.enderecos());
        return proprietarioDomain;
    }

}
