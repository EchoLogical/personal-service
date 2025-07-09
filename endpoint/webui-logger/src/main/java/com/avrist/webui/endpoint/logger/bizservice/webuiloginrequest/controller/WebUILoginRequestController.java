package com.avrist.webui.endpoint.logger.bizservice.webuiloginrequest.controller;

import com.avrist.webui.endpoint.logger.bizservice.webuiloginrequest.WebUILoginRequestService;
import com.avrist.webui.endpoint.logger.bizservice.webuiloginrequest.model.request.WebUIloginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1/login/request")
public class WebUILoginRequestController {

    private final WebUILoginRequestService webUiLoginRequestService;

    @Autowired
    public WebUILoginRequestController(WebUILoginRequestService webUiLoginRequestService) {
        this.webUiLoginRequestService = webUiLoginRequestService;
    }

    @PostMapping("")
    public String login(@RequestBody @Valid WebUIloginRequest request, HttpSession session) {
        return webUiLoginRequestService.execute(WebUIloginRequest.builder()
                        .username(request.getUsername())
                        .password(request.getPassword())
                        .rememberMe(request.getRememberMe())
                        .session(session)
                .build());
    }

}
