package com.github.echological.sc.datasource.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gateway_routes")
public class GatewayRouteEntity {

    @Id
    @Column(name = "id", length = 200, nullable = false)
    private String id;

    @Column(name = "uri", length = 1000, nullable = false)
    private String uri;

    @Column(name = "predicates_text")
    private String predicatesText;

    @Column(name = "filters_text")
    private String filtersText;

    @Column(name = "route_order")
    private Integer routeOrder;

    @Column(name = "metadata")
    private String metadata;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "created_at", nullable = false, columnDefinition = "datetime2", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
