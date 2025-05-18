package com.techchallenge.user_manager_api.dto;

public record AtualizarSenhaRequestDTO(
        String login,
        String senhaAtual,
        String novaSenha
) {
}
