package com.avrist.sc.service.gateway.authenticationfilter;

import com.github.echological.sc.global.contract.BusinessServiceContract;
import com.github.echological.sc.global.exception.BusinessServiceValidationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFilterService implements BusinessServiceContract<Object, Object> {

    @Override
    public Object execute(Object input, String lang) throws BusinessServiceValidationException {
        return null;
    }
}
