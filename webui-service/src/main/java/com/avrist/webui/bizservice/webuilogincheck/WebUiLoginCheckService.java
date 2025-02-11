package com.avrist.webui.bizservice.webuilogincheck;

import com.avrist.webui.bizservice.BusinessService;
import com.avrist.webui.bizservice.webuilogincheck.model.request.WebUIloginRequest;
import com.avrist.webui.bizservice.webuilogincheck.model.response.WebUIloginResponse;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.service.logincheck.LoginCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebUiLoginCheckService implements BusinessService<WebUIloginRequest, WebUIloginResponse> {

    private final LoginCheckService loginCheckService;

    @Autowired
    public WebUiLoginCheckService(LoginCheckService loginCheckService) {
        this.loginCheckService = loginCheckService;
    }

    @Override
    public WebUIloginResponse execute(WebUIloginRequest input) throws BusinessServiceValidationException {
        return WebUIloginResponse.fromLoginCheckOutput(
                loginCheckService.apply(WebUIloginRequest.toLoginCheckInput(input))
        );
    }
}
