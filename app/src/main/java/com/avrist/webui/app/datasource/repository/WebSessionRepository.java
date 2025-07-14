package com.avrist.webui.app.datasource.repository;

import com.avrist.webui.app.datasource.entity.WebSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WebSessionRepository extends JpaRepository<WebSessionEntity, UUID> {

}
