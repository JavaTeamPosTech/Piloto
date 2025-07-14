package com.techchallenge.user_manager_api.domain.entities;

import com.techchallenge.user_manager_api.naousar.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.naousar.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EnderecoDomain {

    private UUID id;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
    private String cep;

    private Usuario usuario;

    public EnderecoDomain(EnderecoRequestDTO dto, Usuario usuario) {
        this.estado = dto.estado();
        this.cidade = dto.cidade();
        this.bairro = dto.bairro();
        this.rua = dto.rua();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.cep = dto.cep();
        this.usuario = usuario;
    }
}
