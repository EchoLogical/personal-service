package com.github.echological.sc.datasource.auth.repository;

import com.github.echological.sc.datasource.auth.entity.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUserEntity, String> {
    Optional<AuthUserEntity> findByUsername(String username);
}
