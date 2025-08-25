package com.github.echological.sc.service.auth.apikeyvalidation;

import com.github.echological.sc.global.constant.ServiceStatus;
import com.github.echological.sc.global.contract.BusinessServiceContract;
import com.github.echological.sc.global.exception.BusinessServiceValidationException;
import com.github.echological.sc.service.auth.apikeyvalidation.helper.ApiKeyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiKeyValidationService implements BusinessServiceContract<String, Boolean> {

    private final ApiKeyValidator apiKeyValidator;

    @Autowired
    public ApiKeyValidationService(ApiKeyValidator apiKeyValidator) {
        this.apiKeyValidator = apiKeyValidator;
    }

    @Override
    public Boolean execute(String apiKey) throws BusinessServiceValidationException {
        if (apiKey == null || apiKey.isBlank()) {
            throw new BusinessServiceValidationException(
                    ServiceStatus.INVALID_ARGUMENT.getCode(),
                    ServiceStatus.INVALID_ARGUMENT.getStatus(),
                    HttpStatus.BAD_REQUEST,
                    List.of("API key must be provided")
            );
        }

        boolean valid = apiKeyValidator.isValid(apiKey);
        if (!valid) {
            throw new BusinessServiceValidationException(
                    ServiceStatus.UNAUTHORIZED.getCode(),
                    ServiceStatus.UNAUTHORIZED.getStatus(),
                    HttpStatus.UNAUTHORIZED,
                    List.of("Invalid or expired API key")
            );
        }
        return true;
    }
}
