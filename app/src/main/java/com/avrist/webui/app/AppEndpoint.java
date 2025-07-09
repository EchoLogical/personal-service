package com.avrist.webui.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan({
        "com.avrist.webui",
        "com.avrist.core"
})
@EnableAsync
public class AppEndpoint {
    public static void main(String[] args) {
        SpringApplication.run(AppEndpoint.class, args);
    }
}
