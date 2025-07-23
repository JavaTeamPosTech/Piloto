package com.techchallenge.user_manager_api.infra.persistence.adapters;

import com.techchallenge.user_manager_api.domain.entities.*;
import com.techchallenge.user_manager_api.infra.model.*;

import java.util.List;

public class RestauranteAdapter {

    public static RestauranteEntity toEntity(RestauranteDomain domain, ProprietarioEntity proprietarioEntity) {
        RestauranteEntity restaurante = new RestauranteEntity(domain.getNome(), proprietarioEntity);
        // setar endereço e outros campos
        return restaurante;
    }



    public static EnderecoRestauranteEntity toEnderecoEntity(EnderecoRestauranteDomain endereco) {
        return new EnderecoRestauranteEntity(endereco.getEstado(), endereco.getCidade(), endereco.getBairro(),
                endereco.getRua(), endereco.getNumero(), endereco.getComplemento(), endereco.getCep());
    }

    public static List<TipoCozinhaEntity> toTiposCozinhaEntity(List<TipoCozinhaDomain> tiposCozinha){
        return tiposCozinha.stream()
                .map(tipoCozinha -> new TipoCozinhaEntity(tipoCozinha.getId(), tipoCozinha.getDescricao()))
                .toList();

    }

    public static ProprietarioEntity toProprietarioEntity(ProprietarioDomain proprietario) {
        ProprietarioEntity proprietarioEntity =  new ProprietarioEntity(proprietario.getCnpj(), proprietario.getRazaoSocial(), proprietario.getNomeFantasia(),
                proprietario.getInscricaoEstadual(), proprietario.getTelefoneComercial(), proprietario.getWhatsapp(), proprietario.getStatusConta(),
                proprietario.getNome(), proprietario.getEmail(), proprietario.getLogin(), proprietario.getSenha(), List.of());

        adicionarEnderecosAoUsuario(proprietarioEntity, proprietario.getEnderecos());
        return proprietarioEntity;
    }

    private static void adicionarEnderecosAoUsuario(UsuarioEntity usuario, List<EnderecoDomain> enderecos) {
        if (enderecos == null || enderecos.isEmpty()) return;

        List<EnderecoEntity> enderecosEntity = enderecos.stream()
                .map(endereco -> new EnderecoEntity(endereco, usuario))
                .toList();

        usuario.getEnderecos().addAll(enderecosEntity);
    }

    public static RestauranteDomain toRestauranteDomain(RestauranteEntity restauranteEntity) {
        return new RestauranteDomain(restauranteEntity.getNome(),
                toProprietarioDomain(restauranteEntity.getProprietario()));
    }

    private static ProprietarioDomain toProprietarioDomain(ProprietarioEntity proprietario) {
         return new ProprietarioDomain(proprietario.getId(), proprietario.getCnpj(), proprietario.getRazaoSocial(), proprietario.getNomeFantasia(),
                proprietario.getInscricaoEstadual(), proprietario.getTelefoneComercial(), proprietario.getWhatsapp(),
                proprietario.getStatusConta(), proprietario.getNome(), proprietario.getEmail(), proprietario.getLogin(),
                proprietario.getSenha());
    }

    private static List<TipoCozinhaDomain> toTiposCozinhaDomain(List<TipoCozinhaEntity> tiposCozinha) {
        return  tiposCozinha.stream()
                .map(tipoCozinha -> new TipoCozinhaDomain(tipoCozinha.getId(), tipoCozinha.getDescricao()))
                .toList();
    }

    private static EnderecoRestauranteDomain toEnderecoDomain(EnderecoRestauranteEntity endereco) {
        return new EnderecoRestauranteDomain(endereco.getEstado(), endereco.getCidade(), endereco.getBairro(),
                endereco.getRua(), endereco.getNumero(), endereco.getComplemento(), endereco.getCep());
    }
}
