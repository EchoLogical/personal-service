package com.github.echological.sc.endpoint.welcome.controller;

import com.github.echological.sc.global.model.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WelcomeController {

    @GetMapping("/hello")
    public BaseResponse<String> hello() {
        return BaseResponse.<String>builder()
                .requestId(UUID.randomUUID().toString())
                .responseCode("ECH-200")
                .responseMessage("Ok")
                .requestPath("/hello")
                .content("Welcome Service")
                .build();
    }
}
