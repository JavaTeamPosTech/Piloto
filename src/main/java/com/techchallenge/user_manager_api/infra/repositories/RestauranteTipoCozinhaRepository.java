package com.techchallenge.user_manager_api.infra.repositories;

import com.techchallenge.user_manager_api.infra.model.RestauranteTipoCozinhaEntity;
import com.techchallenge.user_manager_api.infra.model.RestauranteTipoCozinhaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestauranteTipoCozinhaRepository extends JpaRepository<RestauranteTipoCozinhaEntity, RestauranteTipoCozinhaId> {

    List<RestauranteTipoCozinhaEntity> findByIdRestauranteIdAndIdTipoCozinhaIdIn(UUID restauranteId, List<UUID> tipoIds);

}
