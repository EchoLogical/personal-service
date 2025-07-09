package com.avrist.webui.datasource.webapp.repository;

import com.avrist.webui.datasource.webapp.entity.WebSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WebSessionRepository extends JpaRepository<WebSessionEntity, UUID> {

}
