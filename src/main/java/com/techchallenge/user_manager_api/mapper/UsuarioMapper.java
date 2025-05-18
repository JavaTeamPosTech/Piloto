package com.techchallenge.user_manager_api.mapper;

import com.techchallenge.user_manager_api.dto.*;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.Endereco;
import com.techchallenge.user_manager_api.entities.Proprietario;
import com.techchallenge.user_manager_api.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Cliente toCliente(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente(
                dto.nome(),
                dto.email(),
                dto.login(),
                dto.senha(),
                new ArrayList<>()
        );

        adicionarEnderecosAoUsuario(cliente, dto.enderecos());
        return cliente;
    }

    public static Proprietario toProprietario(ProprietarioRequestDTO dto) {
        Proprietario proprietario = new Proprietario(
                dto.nome(),
                dto.email(),
                dto.login(),
                dto.senha(),
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

    public static ClienteResponseDTO toClienteResponseDTO(Cliente cliente) {

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getLogin(),
                cliente.getUltimaAlteracao(),
                toEnderecoResponseDTO(cliente.getEnderecos()));
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
                proprietario.getNome(),
                proprietario.getEmail(),
                proprietario.getLogin(),
                proprietario.getUltimaAlteracao(),
                toEnderecoResponseDTO(proprietario.getEnderecos())
        );
    }
}
