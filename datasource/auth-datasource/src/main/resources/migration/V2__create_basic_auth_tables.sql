-- Users table for Basic Auth
CREATE TABLE IF NOT EXISTS auth_users (
    id varchar(100) PRIMARY KEY,
    username varchar(200) NOT NULL UNIQUE,
    password varchar(200) NOT NULL,
    enabled boolean NOT NULL DEFAULT TRUE,
    account_non_expired boolean NOT NULL DEFAULT TRUE,
    account_non_locked boolean NOT NULL DEFAULT TRUE,
    credentials_non_expired boolean NOT NULL DEFAULT TRUE,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp
);

-- Roles/Authorities
CREATE TABLE IF NOT EXISTS auth_roles (
    id varchar(100) PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE
);

-- User-Role mapping (many-to-many)
CREATE TABLE IF NOT EXISTS auth_user_roles (
    user_id varchar(100) NOT NULL,
    role_id varchar(100) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES auth_users(id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES auth_roles(id)
);

-- API Keys linked to users
CREATE TABLE IF NOT EXISTS api_keys (
    id varchar(100) PRIMARY KEY,
    user_id varchar(100) NOT NULL,
    api_key varchar(200) NOT NULL UNIQUE,
    description varchar(255),
    enabled boolean NOT NULL DEFAULT TRUE,
    expires_at timestamp,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_api_keys_user FOREIGN KEY (user_id) REFERENCES auth_users(id)
);
