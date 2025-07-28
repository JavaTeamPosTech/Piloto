package com.techchallenge.user_manager_api.domain.entities;

import com.techchallenge.user_manager_api.application.inputs.EnderecoInput;
import com.techchallenge.user_manager_api.domain.dto.requests.EnderecoRequestDTO;
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
    private UsuarioDomain usuario;

    public EnderecoDomain(EnderecoRequestDTO dto, UsuarioDomain usuario) {
        this.estado = dto.estado();
        this.cidade = dto.cidade();
        this.bairro = dto.bairro();
        this.rua = dto.rua();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.cep = dto.cep();
        this.usuario = usuario;
    }

    public EnderecoDomain(EnderecoRequestDTO dto) {
        this.estado = dto.estado();
        this.cidade = dto.cidade();
        this.bairro = dto.bairro();
        this.rua = dto.rua();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.cep = dto.cep();
    }

    public EnderecoDomain(EnderecoInput enderecoInput, UsuarioDomain usuarioDomain) {
        this.estado = enderecoInput.getEstado();
        this.cidade = enderecoInput.getCidade();
        this.bairro = enderecoInput.getBairro();
        this.rua = enderecoInput.getRua();
        this.numero = enderecoInput.getNumero();
        this.complemento = enderecoInput.getComplemento();
        this.cep = enderecoInput.getCep();
        this.usuario = usuarioDomain;

    }


    public EnderecoDomain atualizarCom(EnderecoInput input) {
        if (input.getEstado() != null) {
            this.estado = input.getEstado();
        }
        if (input.getCidade() != null) {
            this.cidade = input.getCidade();
        }
        if (input.getBairro() != null) {
            this.bairro = input.getBairro();
        }
        if (input.getRua() != null) {
            this.rua = input.getRua();
        }
        if (input.getNumero() != null) {
            this.numero = input.getNumero();
        }
        if (input.getComplemento() != null) {
            this.complemento = input.getComplemento();
        }
        if (input.getCep() != null) {
            this.cep = input.getCep();
        }
        return this;
    }
}
