package com.github.echological.sc.endpoint.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class GatewayFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GatewayFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Ambil header x-api-key (header bersifat case-insensitive)
        String apiKey = exchange.getRequest().getHeaders().getFirst("x-api-key");

        if (apiKey == null || apiKey.isBlank()) {
            log.warn("Missing x-api-key header for {}", exchange.getRequest().getURI());
            var response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
            byte[] bytes = "Missing x-api-key header".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        }

        log.debug("x-api-key received");

        // Opsional: normalisasi header untuk diteruskan downstream dengan nama konsisten
        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .headers(h -> h.set("X-API-KEY", apiKey))
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
