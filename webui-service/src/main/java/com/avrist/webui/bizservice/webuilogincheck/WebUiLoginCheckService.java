package com.avrist.webui.bizservice.webuilogincheck;

import com.avrist.webui.bizservice.BusinessService;
import com.avrist.webui.bizservice.webuilogincheck.model.request.WebUIloginCheckRequest;
import com.avrist.webui.bizservice.webuilogincheck.model.response.WebUIloginCheckResponse;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.bizservice.webuilogincheck.helper.LoginCheckHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebUiLoginCheckService implements BusinessService<WebUIloginCheckRequest, WebUIloginCheckResponse> {

    private final LoginCheckHelper loginCheckHelper;

    @Autowired
    public WebUiLoginCheckService(LoginCheckHelper loginCheckHelper) {
        this.loginCheckHelper = loginCheckHelper;
    }

    @Override
    public WebUIloginCheckResponse execute(WebUIloginCheckRequest input) throws BusinessServiceValidationException {
        var credentialsCheck = loginCheckHelper.check(WebUIloginCheckRequest.toLoginCheckInput(input));
        if(ObjectUtils.isEmpty(credentialsCheck))
            return null;
        return WebUIloginCheckResponse.fromLoginCheckOutput(credentialsCheck);
    }
}
