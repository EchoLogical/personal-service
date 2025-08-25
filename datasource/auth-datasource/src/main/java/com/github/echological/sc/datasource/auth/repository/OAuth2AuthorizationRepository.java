package com.github.echological.sc.datasource.auth.repository;

import com.github.echological.sc.datasource.auth.entity.OAuth2AuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2AuthorizationRepository extends JpaRepository<OAuth2AuthorizationEntity, String> {
    Optional<OAuth2AuthorizationEntity> findByState(String state);
    Optional<OAuth2AuthorizationEntity> findByAccessTokenValue(byte[] accessTokenValue);
    Optional<OAuth2AuthorizationEntity> findByRefreshTokenValue(byte[] refreshTokenValue);
    Optional<OAuth2AuthorizationEntity> findByAuthorizationCodeValue(byte[] authorizationCodeValue);
}
