package com.avrist.webui.datasource.webapp.repository;

import com.avrist.webui.datasource.webapp.entity.AppConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppConfigurationRepository extends JpaRepository<AppConfigurationEntity, UUID> {
    @Query(value = "FROM AppConfigurationEntity ac WHERE ac.configName = :configName")
    Optional<AppConfigurationEntity> findByName(@Param("configName") String configName);
}
