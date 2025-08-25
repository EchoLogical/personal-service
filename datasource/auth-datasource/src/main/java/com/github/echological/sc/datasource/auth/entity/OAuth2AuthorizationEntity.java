package com.github.echological.sc.datasource.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oauth2_authorization")
public class OAuth2AuthorizationEntity {

    @Id
    @Column(name = "id", length = 100, nullable = false)
    private String id;

    @Column(name = "registered_client_id", length = 100, nullable = false)
    private String registeredClientId;

    @Column(name = "principal_name", length = 200, nullable = false)
    private String principalName;

    @Column(name = "authorization_grant_type", length = 100, nullable = false)
    private String authorizationGrantType;

    @Lob
    @Column(name = "attributes")
    private String attributes;

    @Column(name = "state", length = 500)
    private String state;

    @Column(name = "authorization_code_value")
    private byte[] authorizationCodeValue;

    @Column(name = "authorization_code_issued_at")
    private LocalDateTime authorizationCodeIssuedAt;

    @Column(name = "authorization_code_expires_at")
    private LocalDateTime authorizationCodeExpiresAt;

    @Lob
    @Column(name = "authorization_code_metadata")
    private String authorizationCodeMetadata;

    @Column(name = "access_token_value")
    private byte[] accessTokenValue;

    @Column(name = "access_token_issued_at")
    private LocalDateTime accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;

    @Lob
    @Column(name = "access_token_metadata")
    private String accessTokenMetadata;

    @Column(name = "access_token_type", length = 100)
    private String accessTokenType;

    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @Column(name = "oidc_id_token_value")
    private byte[] oidcIdTokenValue;

    @Column(name = "oidc_id_token_issued_at")
    private LocalDateTime oidcIdTokenIssuedAt;

    @Column(name = "oidc_id_token_expires_at")
    private LocalDateTime oidcIdTokenExpiresAt;

    @Lob
    @Column(name = "oidc_id_token_metadata")
    private String oidcIdTokenMetadata;

    @Column(name = "refresh_token_value")
    private byte[] refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private LocalDateTime refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private LocalDateTime refreshTokenExpiresAt;

    @Lob
    @Column(name = "refresh_token_metadata")
    private String refreshTokenMetadata;

    @Column(name = "user_code_value")
    private byte[] userCodeValue;

    @Column(name = "user_code_issued_at")
    private LocalDateTime userCodeIssuedAt;

    @Column(name = "user_code_expires_at")
    private LocalDateTime userCodeExpiresAt;

    @Lob
    @Column(name = "user_code_metadata")
    private String userCodeMetadata;

    @Column(name = "device_code_value")
    private byte[] deviceCodeValue;

    @Column(name = "device_code_issued_at")
    private LocalDateTime deviceCodeIssuedAt;

    @Column(name = "device_code_expires_at")
    private LocalDateTime deviceCodeExpiresAt;

    @Lob
    @Column(name = "device_code_metadata")
    private String deviceCodeMetadata;
}
