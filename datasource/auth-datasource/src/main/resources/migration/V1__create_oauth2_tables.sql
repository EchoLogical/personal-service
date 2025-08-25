-- OAuth2 Registered Client
CREATE TABLE IF NOT EXISTS oauth2_registered_client (
    id varchar(100) PRIMARY KEY,
    client_id varchar(100) NOT NULL UNIQUE,
    client_id_issued_at timestamp NOT NULL,
    client_secret varchar(200),
    client_secret_expires_at timestamp,
    client_name varchar(200) NOT NULL,
    client_authentication_methods varchar(1000) NOT NULL,
    authorization_grant_types varchar(1000) NOT NULL,
    redirect_uris varchar(1000),
    scopes varchar(1000),
    client_settings text NOT NULL,
    token_settings text NOT NULL
);

-- OAuth2 Authorization
CREATE TABLE IF NOT EXISTS oauth2_authorization (
    id varchar(100) PRIMARY KEY,
    registered_client_id varchar(100) NOT NULL,
    principal_name varchar(200) NOT NULL,
    authorization_grant_type varchar(100) NOT NULL,
    attributes text,
    state varchar(500),

    authorization_code_value bytea,
    authorization_code_issued_at timestamp,
    authorization_code_expires_at timestamp,
    authorization_code_metadata text,

    access_token_value bytea,
    access_token_issued_at timestamp,
    access_token_expires_at timestamp,
    access_token_metadata text,
    access_token_type varchar(100),
    access_token_scopes varchar(1000),

    oidc_id_token_value bytea,
    oidc_id_token_issued_at timestamp,
    oidc_id_token_expires_at timestamp,
    oidc_id_token_metadata text,

    refresh_token_value bytea,
    refresh_token_issued_at timestamp,
    refresh_token_expires_at timestamp,
    refresh_token_metadata text,

    user_code_value bytea,
    user_code_issued_at timestamp,
    user_code_expires_at timestamp,
    user_code_metadata text,

    device_code_value bytea,
    device_code_issued_at timestamp,
    device_code_expires_at timestamp,
    device_code_metadata text
);

-- OAuth2 Authorization Consent
CREATE TABLE IF NOT EXISTS oauth2_authorization_consent (
    registered_client_id varchar(100) NOT NULL,
    principal_name varchar(200) NOT NULL,
    authorities varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);
