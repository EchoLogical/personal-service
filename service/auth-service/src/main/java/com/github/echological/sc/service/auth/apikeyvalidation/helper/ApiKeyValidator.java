package com.github.echological.sc.service.auth.apikeyvalidation.helper;

import com.github.echological.sc.datasource.auth.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApiKeyValidator {

    private final ApiKeyRepository apiKeyRepository;

    @Autowired
    public ApiKeyValidator(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    /**
     * Validates if an API key exists, is enabled, and either non-expiring or not expired yet.
     */
    public boolean isValid(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return apiKeyRepository.existsByApiKeyAndEnabledIsTrueAndExpiresAtIsNull(apiKey)
                || apiKeyRepository.existsByApiKeyAndEnabledIsTrueAndExpiresAtAfter(apiKey, now);
    }
}
