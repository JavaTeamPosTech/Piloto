package com.techchallenge.user_manager_api.dto.response;

import com.techchallenge.user_manager_api.entities.Cliente;
import com.techchallenge.user_manager_api.entities.Proprietario;

import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        String login
) {
    public static UsuarioResponseDTO deCliente(Cliente cliente) {
        return new UsuarioResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getLogin()
        );
    }

    public static UsuarioResponseDTO deProprietario(Proprietario proprietario) {
        return new UsuarioResponseDTO(
                proprietario.getId(),
                proprietario.getNome(),
                proprietario.getEmail(),
                proprietario.getLogin()
        );
    }
}
