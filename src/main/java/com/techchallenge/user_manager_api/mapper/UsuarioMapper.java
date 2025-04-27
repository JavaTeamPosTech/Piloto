package com.techchallenge.user_manager_api.mapper;

import com.techchallenge.user_manager_api.dto.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.Endereco;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Endereco> enderecos = mapEnderecos(dto.enderecos(), cliente);
        cliente.getEnderecos().addAll(enderecos);

        return cliente;
    }

    private static List<Endereco> mapEnderecos(List<EnderecoRequestDTO> dtos, Cliente cliente) {
        if (dtos == null || dtos.isEmpty()) return new ArrayList<>();
        return dtos.stream()
                .map(enderecoDTO -> new Endereco(enderecoDTO, cliente))
                .collect(Collectors.toList());
    }
}
