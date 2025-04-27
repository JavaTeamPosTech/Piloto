package com.techchallenge.user_manager_api.mapper;

import com.techchallenge.user_manager_api.dto.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.dto.ProprietarioRequestDTO;
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
}
