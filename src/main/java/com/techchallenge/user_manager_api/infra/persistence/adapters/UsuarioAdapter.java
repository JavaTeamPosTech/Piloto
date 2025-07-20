package com.techchallenge.user_manager_api.infra.persistence.adapters;

import com.techchallenge.user_manager_api.domain.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.domain.entities.EnderecoDomain;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import com.techchallenge.user_manager_api.domain.entities.UsuarioDomain;
import com.techchallenge.user_manager_api.infra.model.ClienteEntity;
import com.techchallenge.user_manager_api.infra.model.EnderecoEntity;
import com.techchallenge.user_manager_api.infra.model.ProprietarioEntity;
import com.techchallenge.user_manager_api.infra.model.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

import static com.techchallenge.user_manager_api.application.mappers.UsuarioMapper.toEnderecoResponseDTO;

public class UsuarioAdapter {

    public static ClienteEntity toCliente(ClienteDomain clienteDomain, String senhaCriptografada) {
        ClienteEntity cliente = new ClienteEntity(
                clienteDomain.getCpf(),
                clienteDomain.getDataNascimento(),
                clienteDomain.getGenero(),
                clienteDomain.getTelefone(),
                clienteDomain.getPreferenciasAlimentares(),
                clienteDomain.getAlergias(),
                clienteDomain.getMetodoPagamentoPreferido(),
                clienteDomain.getClienteVip(),
                clienteDomain.getNotificacoesAtivas(),
                clienteDomain.getNome(),
                clienteDomain.getEmail(),
                clienteDomain.getLogin(),
                senhaCriptografada,
                new ArrayList<>()
        );

        adicionarEnderecosAoUsuario(cliente, clienteDomain.getEnderecos());
        return cliente;
    }


    public static ClienteDomain toClienteDomain(ClienteEntity clienteEntity) {
        return new ClienteDomain(
                clienteEntity.getId(),
                clienteEntity.getCpf(),
                clienteEntity.getDataNascimento(),
                clienteEntity.getGenero(),
                clienteEntity.getTelefone(),
                clienteEntity.getPreferenciasAlimentares(),
                clienteEntity.getAlergias(),
                clienteEntity.getMetodoPagamentoPreferido(),
                clienteEntity.getClienteVip(),
                clienteEntity.getNome(),
                clienteEntity.getEmail(),
                clienteEntity.getLogin(),
                clienteEntity.getSenha()
                );
    }



    public static ProprietarioEntity toProprietario(ProprietarioDomain proprietarioDomain, String senhaCriptografada) {
        ProprietarioEntity proprietario = new ProprietarioEntity(
                proprietarioDomain.getCnpj(),
                proprietarioDomain.getRazaoSocial(),
                proprietarioDomain.getNomeFantasia(),
                proprietarioDomain.getInscricaoEstadual(),
                proprietarioDomain.getTelefoneComercial(),
                proprietarioDomain.getWhatsapp(),
                proprietarioDomain.getStatusConta(),
                proprietarioDomain.getNome(),
                proprietarioDomain.getEmail(),
                proprietarioDomain.getLogin(),
                senhaCriptografada,
                new ArrayList<>()
        );

        adicionarEnderecosAoUsuario(proprietario, proprietarioDomain.getEnderecos());
        return proprietario;
    }

    public static ProprietarioDomain toProprietarioDomain(ProprietarioEntity proprietarioEntity) {
        return new ProprietarioDomain(
                proprietarioEntity.getId(),
                proprietarioEntity.getCnpj(),
                proprietarioEntity.getRazaoSocial(),
                proprietarioEntity.getNomeFantasia(),
                proprietarioEntity.getInscricaoEstadual(),
                proprietarioEntity.getTelefoneComercial(),
                proprietarioEntity.getWhatsapp(),
                proprietarioEntity.getStatusConta(),
                proprietarioEntity.getNome(),
                proprietarioEntity.getEmail(),
                proprietarioEntity.getLogin(),
                proprietarioEntity.getSenha()
        );
    }

    public static UsuarioDomain toUsuarioDomain(UsuarioEntity usuarioEntity) {
        return new UsuarioDomain(
                usuarioEntity.getId(),
                usuarioEntity.getNome(),
                usuarioEntity.getEmail(),
                usuarioEntity.getLogin(),
                usuarioEntity.getSenha()
        );
    }

    public static LoginResponseDTO toLoginResponseDto(String token) {
        return new LoginResponseDTO(token);
    }
//
//    public static UsuarioDomain toUsuarioDomain(UsuarioEntity usuarioEntity) {
//        return new UsuarioDomain(
//                usuarioEntity.getId(),
//                usuarioEntity.getNome(),
//                usuarioEntity.getEmail(),
//                usuarioEntity.getLogin(),
//                usuarioEntity.getSenha()
//        );
//    }

//    public static Proprietario toProprietario(ProprietarioRequestDTO dto, String senhaCriptografada) {
//        Proprietario proprietario = new Proprietario(
//                dto.cnpj(),
//                dto.razaoSocial(),
//                dto.nomeFantasia(),
//                dto.inscricaoEstadual(),
//                dto.telefoneComercial(),
//                dto.whatsapp(),
//                dto.statusConta(),
//                dto.nome(),
//                dto.email(),
//                dto.login(),
//                senhaCriptografada,
//                new ArrayList<>()
//        );
//
//        adicionarEnderecosAoUsuario(proprietario, dto.enderecos());
//
//        return proprietario;
//    }
//
    private static void adicionarEnderecosAoUsuario(UsuarioEntity usuario, List<EnderecoDomain> enderecos) {
        if (enderecos == null || enderecos.isEmpty()) return;

        List<EnderecoEntity> enderecosEntity = enderecos.stream()
                .map(endereco -> new EnderecoEntity(endereco, usuario))
                .toList();

        usuario.getEnderecos().addAll(enderecosEntity);
    }
//
//    public static ClienteResponseDTO toClienteResponseDTO(Cliente cliente) {
//
//        return new ClienteResponseDTO(
//                cliente.getId(),
//                cliente.getNome(),
//                cliente.getCpf(),
//                cliente.getDataNascimento(),
//                cliente.getEmail(),
//                cliente.getLogin(),
//                cliente.getTelefone(),
//                toEnderecoResponseDTO(cliente.getEnderecos()));
//    }
//
//    private static List<EnderecoResponseDTO> toEnderecoResponseDTO(List<Endereco> enderecos) {
//
//        return enderecos.stream().map(e -> new EnderecoResponseDTO(
//                e.getId(),
//                e.getEstado(),
//                e.getCidade(),
//                e.getBairro(),
//                e.getRua(),
//                e.getNumero(),
//                e.getComplemento(),
//                e.getCep()
//        )).toList();
//    }
//
    public static ProprietarioResponseDTO toProprietarioResponseDTO(ProprietarioDomain proprietario) {
        return new ProprietarioResponseDTO(
                proprietario.getId(),
                proprietario.getCnpj(),
                proprietario.getRazaoSocial(),
                proprietario.getNomeFantasia(),
                proprietario.getInscricaoEstadual(),
                proprietario.getTelefoneComercial(),
                proprietario.getWhatsapp(),
                proprietario.getNome(),
                proprietario.getEmail(),
                proprietario.getLogin(),
                toEnderecoResponseDTO(proprietario.getEnderecos())
        );

    }

}
