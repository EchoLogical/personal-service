package com.github.echological.sc.datasource.auth.repository;

import com.github.echological.sc.datasource.auth.entity.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, String> {
    Optional<ApiKeyEntity> findByApiKey(String apiKey);

    boolean existsByApiKeyAndEnabledIsTrueAndExpiresAtIsNull(String apiKey);

    boolean existsByApiKeyAndEnabledIsTrueAndExpiresAtAfter(String apiKey, LocalDateTime now);
}
