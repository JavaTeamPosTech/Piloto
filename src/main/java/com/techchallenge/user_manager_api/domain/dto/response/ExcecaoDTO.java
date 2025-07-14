package com.techchallenge.user_manager_api.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ExcecaoDTO(
        String mensagem,
        int status,
        LocalDateTime timestamp,
        List<ErroValidacaoDTO> erros
) {}