package com.github.echological.sc.endpoint.gateway.route;

import com.github.echological.sc.datasource.gateway.repository.GatewayRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WelcomeService {

    @Autowired
    private GatewayRouteRepository gatewayRouteRepository;

    @Bean
    public RouteLocator welcomeRoute(RouteLocatorBuilder builder) {
        var serviceInfo = gatewayRouteRepository.findByServiceIdAndEnabled("welcome-service", true);

        if(serviceInfo.isEmpty()){
            return null;
        }

        return builder.routes()
                .route(serviceInfo.get().getServiceId(),
                        r -> r.path(serviceInfo.get().getFromPath())
                                .filters(f -> f.setPath(serviceInfo.get().getToPath()))
                                .uri(serviceInfo.get().getUri()))
                .build();
    }


}
