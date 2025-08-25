package com.github.echological.sc.datasource.auth.repository;

import com.github.echological.sc.datasource.auth.entity.OAuth2RegisteredClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2RegisteredClientRepository extends JpaRepository<OAuth2RegisteredClientEntity, String> {
    Optional<OAuth2RegisteredClientEntity> findByClientId(String clientId);
}
