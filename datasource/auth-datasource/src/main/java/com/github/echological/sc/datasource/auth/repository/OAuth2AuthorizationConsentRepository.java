package com.github.echological.sc.datasource.auth.repository;

import com.github.echological.sc.datasource.auth.entity.OAuth2AuthorizationConsentEntity;
import com.github.echological.sc.datasource.auth.entity.OAuth2AuthorizationConsentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2AuthorizationConsentRepository extends JpaRepository<OAuth2AuthorizationConsentEntity, OAuth2AuthorizationConsentId> {
}
