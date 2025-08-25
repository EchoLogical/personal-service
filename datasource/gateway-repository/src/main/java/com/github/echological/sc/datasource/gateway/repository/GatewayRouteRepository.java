package com.github.echological.sc.datasource.gateway.repository;

import com.github.echological.sc.datasource.gateway.entity.GatewayRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GatewayRouteRepository extends JpaRepository<GatewayRouteEntity, String> {

    @Query(value = "SELECT * FROM gateway_routes WHERE enabled = 1 ORDER BY CASE WHEN route_order IS NULL THEN 1 ELSE 0 END, route_order", nativeQuery = true)
    List<GatewayRouteEntity> findAllEnabledOrdered();
}
