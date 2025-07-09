package com.avrist.webui.endpoint.logger.service.appconfig.impl;

import com.avrist.webui.datasource.webapp.entity.AppConfigurationEntity;
import com.avrist.webui.datasource.webapp.repository.AppConfigurationRepository;
import com.avrist.webui.endpoint.logger.service.appconfig.AppConfigService;
import com.avrist.webui.endpoint.logger.service.appconfig.model.input.AppConfigInput;
import com.avrist.webui.global.exception.GeneralServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppConfigServiceImpl implements AppConfigService {

    private final AppConfigurationRepository appConfigurationRepository;

    @Autowired
    public AppConfigServiceImpl(AppConfigurationRepository appConfigurationRepository) {
        this.appConfigurationRepository = appConfigurationRepository;
    }

    @Override
    public AppConfigurationEntity load(AppConfigInput input) throws GeneralServiceValidationException {
        var existingConfig = appConfigurationRepository.findByName(input.getConfigName());

        if(existingConfig.isPresent())
            return existingConfig.get();

        return appConfigurationRepository.save(AppConfigurationEntity.builder()
                        .id(UUID.randomUUID())
                        .configName(input.getConfigName())
                        .configDesc(input.getConfigDesc())
                        .configValue(input.getConfigDefaultValue())
                .build());
    }
}
