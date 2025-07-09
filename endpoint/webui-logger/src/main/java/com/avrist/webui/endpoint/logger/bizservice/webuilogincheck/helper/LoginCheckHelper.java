package com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.helper;

import com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.model.helper.LoginCheck;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginCheckHelper {

    @Autowired
    public LoginCheckHelper() {
    }

    public LoginCheck.Output check(LoginCheck.Input input) throws BusinessServiceValidationException {
        return null;
    }
}
