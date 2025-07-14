package com.techchallenge.user_manager_api.infra.repositories;

import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.infra.model.ClienteEntity;
import com.techchallenge.user_manager_api.naousar.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.EnderecoResponseDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.naousar.entities.Endereco;
import com.techchallenge.user_manager_api.naousar.entities.Proprietario;
import com.techchallenge.user_manager_api.naousar.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static ClienteEntity toCliente(ClienteDomain dto, String senhaCriptografada) {
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

    public static Proprietario toProprietario(ProprietarioRequestDTO dto, String senhaCriptografada) {
        Proprietario proprietario = new Proprietario(
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
                senhaCriptografada,
                new ArrayList<>()
        );

        adicionarEnderecosAoUsuario(proprietario, dto.enderecos());

        return proprietario;
    }

    private static void adicionarEnderecosAoUsuario(Usuario usuario, List<EnderecoRequestDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) return;

        List<Endereco> enderecos = dtos.stream()
                .map(enderecoDTO -> new Endereco(enderecoDTO, usuario))
                .toList();

        usuario.getEnderecos().addAll(enderecos);
    }

    public static ClienteResponseDTO toClienteResponseDTO(ClienteDomain clienteDomain) {

        return new ClienteResponseDTO(
                clienteDomain.getId(),
                clienteDomain.getNome(),
                clienteDomain.getCpf(),
                clienteDomain.getDataNascimento(),
                clienteDomain.getEmail(),
                clienteDomain.getLogin(),
                clienteDomain.getTelefone(),
                toEnderecoResponseDTO(clienteDomain.getEnderecos()));
    }

    private static List<EnderecoResponseDTO> toEnderecoResponseDTO(List<Endereco> enderecos) {

        return enderecos.stream().map(e -> new EnderecoResponseDTO(
                e.getId(),
                e.getEstado(),
                e.getCidade(),
                e.getBairro(),
                e.getRua(),
                e.getNumero(),
                e.getComplemento(),
                e.getCep()
        )).toList();
    }

    public static ProprietarioResponseDTO toProprietarioResponseDTO(Proprietario proprietario) {
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
