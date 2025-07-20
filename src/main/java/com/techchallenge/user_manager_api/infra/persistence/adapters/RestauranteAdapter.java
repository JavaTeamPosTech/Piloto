package com.techchallenge.user_manager_api.infra.persistence.adapters;

import com.techchallenge.user_manager_api.domain.entities.EnderecoRestauranteDomain;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;
import com.techchallenge.user_manager_api.infra.model.*;

import java.util.List;

public class RestauranteAdapter {

    public static RestauranteEntity toEntity(RestauranteDomain restauranteDomain) {
        return new RestauranteEntity(restauranteDomain.getNome(), toEnderecoEntity(restauranteDomain.getEndereco()),
                , UsuarioAdapter.toProprietario(restauranteDomain.getProprietario()));
    }

    public static EnderecoRestauranteEntity toEnderecoEntity(EnderecoRestauranteDomain endereco) {
        return new EnderecoRestauranteEntity(endereco.getEstado(), endereco.getCidade(), endereco.getBairro(),
                endereco.getRua(), endereco.getNumero(), endereco.getComplemento(), endereco.getCep());
    }
    public static List<TipoCozinhaEntity> toTiposCozinhaEntity(List<TipoCozinhaDomain> tiposCozinha){

    }

    public static ProprietarioEntity toProprietarioEntity(ProprietarioDomain proprietario) {
        new ProprietarioEntity(proprietario.getCnpj(), proprietario.getRazaoSocial(), proprietario.getNomeFantasia(),
                proprietario.getInscricaoEstadual(), proprietario.getTelefoneComercial(), proprietario.getWhatsapp(), proprietario.getStatusConta(),
                proprietario.getNome(), proprietario.getEmail(), proprietario.getLogin(), proprietario.getSenha(), proprietario.getEnderecos());
    }
}
