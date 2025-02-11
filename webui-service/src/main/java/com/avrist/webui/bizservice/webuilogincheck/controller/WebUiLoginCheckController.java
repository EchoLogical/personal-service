package com.avrist.webui.bizservice.webuilogincheck.controller;

import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.bizservice.webuilogincheck.WebUiLoginCheckService;
import com.avrist.webui.bizservice.webuilogincheck.model.request.WebUIloginRequest;
import com.avrist.webui.bizservice.webuilogincheck.model.response.WebUIloginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/login/validate")
public class WebUiLoginCheckController {

    private final WebUiLoginCheckService webUILoginCheckService;

    @Autowired
    public WebUiLoginCheckController(WebUiLoginCheckService webUILoginCheckService) {
        this.webUILoginCheckService = webUILoginCheckService;
    }

    @PostMapping("")
    public ResponseEntity<WebUIloginResponse> validateCredentials(
            @RequestBody @Valid WebUIloginRequest request) throws BusinessServiceValidationException {
        return ResponseEntity
                .ok()
                .body(webUILoginCheckService.execute(request));
    }

}
