package com.github.echological.sc.datasource.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2AuthorizationConsentId implements Serializable {
    private String registeredClientId;
    private String principalName;
}
