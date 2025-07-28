package com.techchallenge.user_manager_api.application.inputs;

import com.techchallenge.user_manager_api.domain.dto.requests.EnderecoRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EnderecoInput {

    private UUID id;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
    private String cep;

    private EnderecoInput(
            UUID id,
            String estado,
            String cidade,
            String bairro,
            String rua,
            Integer numero,
            String complemento,
            String cep
    ) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
    }

    public static EnderecoInput from(EnderecoRequestDTO dto) {
        return new EnderecoInput(
                dto.id(),
                dto.estado(),
                dto.cidade(),
                dto.bairro(),
                dto.rua(),
                dto.numero(),
                dto.complemento(),
                dto.cep()
        );
    }


}

