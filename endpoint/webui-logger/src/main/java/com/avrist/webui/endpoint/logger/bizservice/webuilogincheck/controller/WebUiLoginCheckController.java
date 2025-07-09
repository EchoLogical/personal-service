package com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.controller;

import com.avrist.webui.global.constant.AVRStatus;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.WebUiLoginCheckService;
import com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.model.request.WebUIloginCheckRequest;
import com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.model.response.WebUIloginCheckResponse;
import com.avrist.webui.global.model.BaseResponse;
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
    public ResponseEntity<BaseResponse<WebUIloginCheckResponse>> validateCredentials(
            @RequestBody @Valid WebUIloginCheckRequest request) throws BusinessServiceValidationException {
        return ResponseEntity
                .ok()
                .body(BaseResponse.<WebUIloginCheckResponse>builder()
                        .responseCode(AVRStatus.OK.getCode())
                        .content(webUILoginCheckService.execute(request))
                        .build());
    }

}
