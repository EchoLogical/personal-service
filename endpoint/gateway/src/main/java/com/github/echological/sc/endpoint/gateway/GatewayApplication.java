package com.github.echological.sc.endpoint.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Entry point for the Spring Cloud Gateway application.
 *
 * <p>Bootstraps the application context and starts the embedded server.</p>
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.github.echological.sc.endpoint.gateway",
        "com.github.echological.sc.datasource.gateway"
})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
