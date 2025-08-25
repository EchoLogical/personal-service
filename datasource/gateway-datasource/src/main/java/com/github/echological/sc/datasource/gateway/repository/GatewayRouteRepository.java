package com.github.echological.sc.datasource.gateway.repository;

import com.github.echological.sc.datasource.gateway.entity.GatewayRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GatewayRouteRepository extends JpaRepository<GatewayRouteEntity, String> {
    Optional<GatewayRouteEntity> findByServiceIdAndEnabled(String serviceId, boolean enabled);
}
