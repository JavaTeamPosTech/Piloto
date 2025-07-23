package com.techchallenge.user_manager_api.application.usecases.restaurante;

import com.techchallenge.user_manager_api.api.controllers.gateways.EnderecoRestauranteGatewayRepository;
import com.techchallenge.user_manager_api.api.controllers.gateways.ProprietarioGatewayRepository;
import com.techchallenge.user_manager_api.api.controllers.gateways.RestauranteGatewayRepository;
import com.techchallenge.user_manager_api.api.controllers.gateways.TipoCozinhaGatewayRepository;
import com.techchallenge.user_manager_api.application.mappers.EnderecoRestauranteMapper;
import com.techchallenge.user_manager_api.application.mappers.RestauranteMapper;
import com.techchallenge.user_manager_api.application.usecases.presenters.RestaurantePresenter;
import com.techchallenge.user_manager_api.domain.dto.requests.RestauranteRequestDTO;
import com.techchallenge.user_manager_api.domain.entities.EnderecoRestauranteDomain;
import com.techchallenge.user_manager_api.domain.entities.ProprietarioDomain;
import com.techchallenge.user_manager_api.domain.entities.RestauranteDomain;
import com.techchallenge.user_manager_api.domain.entities.TipoCozinhaDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastrarRestauranteUseCase {

    private final RestauranteGatewayRepository restauranteRepository;
    private final RestaurantePresenter  restaurantePresenter;
    private final ProprietarioGatewayRepository  proprietarioRepository;
    private final EnderecoRestauranteGatewayRepository enderecoRestauranteGatewayRepository;
    private final TipoCozinhaGatewayRepository tipoCozinhaRepository;

    public CadastrarRestauranteUseCase(RestauranteGatewayRepository restauranteRepository,
                                       RestaurantePresenter restaurantePresenter, ProprietarioGatewayRepository  proprietarioRepository,
                                       EnderecoRestauranteGatewayRepository enderecoRestauranteGatewayRepository,
                                       TipoCozinhaGatewayRepository tipoCozinhaRepository){
        this.restauranteRepository = restauranteRepository;
        this.restaurantePresenter = restaurantePresenter;
        this.proprietarioRepository = proprietarioRepository;
        this.enderecoRestauranteGatewayRepository =  enderecoRestauranteGatewayRepository;
        this.tipoCozinhaRepository = tipoCozinhaRepository;
    }

    public void executar(RestauranteRequestDTO dto){

        //verifica se o proprietario existe
        ProprietarioDomain proprietarioDomain = proprietarioRepository.buscarProprietarioPorId(dto.proprietarioId());

        //Busca tipos cozinha domain e verifica se existe
        List<TipoCozinhaDomain> tipoCozinhaDomains = tipoCozinhaRepository.buscarTiposPorId(dto.tiposCozinhaId());

        //cadastrar endereco do restaurante
        EnderecoRestauranteDomain enderecoRestauranteDomain = EnderecoRestauranteMapper.toDomain(dto.endereco());


        RestauranteDomain restauranteDomain = RestauranteMapper.toDomain(dto, proprietarioDomain);
        restauranteRepository.cadastrarRestaurante(restauranteDomain);
        restaurantePresenter.apresentar(restauranteDomain);
    }
}
