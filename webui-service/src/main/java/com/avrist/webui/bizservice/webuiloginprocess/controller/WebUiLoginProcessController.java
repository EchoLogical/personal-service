package com.avrist.webui.bizservice.webuiloginprocess.controller;

import com.avrist.webui.bizservice.webuiloginprocess.WebUiLoginProcessService;
import com.avrist.webui.bizservice.webuiloginprocess.model.request.WebUIloginProcessRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1/login/process")
public class WebUiLoginProcessController {

    private final WebUiLoginProcessService webUiLoginProcessService;

    @Autowired
    public WebUiLoginProcessController(WebUiLoginProcessService webUiLoginProcessService) {
        this.webUiLoginProcessService = webUiLoginProcessService;
    }

    @PostMapping("")
    public String login(@RequestBody @Valid WebUIloginProcessRequest request) {
        return webUiLoginProcessService.execute(request);
    }

}
