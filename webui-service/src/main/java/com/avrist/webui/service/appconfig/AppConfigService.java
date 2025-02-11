package com.avrist.webui.service.appconfig;

import com.avrist.webui.datasource.entity.AppConfigurationEntity;
import com.avrist.webui.datasource.repository.AppConfigurationRepository;
import com.avrist.webui.global.exception.GeneralServiceValidationException;
import com.avrist.webui.service.GeneralService;
import com.avrist.webui.service.appconfig.model.input.AppConfigInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppConfigService implements GeneralService<AppConfigInput, AppConfigurationEntity> {

    private final AppConfigurationRepository appConfigurationRepository;

    @Autowired
    public AppConfigService(AppConfigurationRepository appConfigurationRepository) {
        this.appConfigurationRepository = appConfigurationRepository;
    }

    @Override
    public AppConfigurationEntity apply(AppConfigInput input) throws GeneralServiceValidationException {
        var existingConfig = appConfigurationRepository.findByName(input.getConfigName());

        if(existingConfig.isPresent())
            return existingConfig.get();

        return appConfigurationRepository.save(AppConfigurationEntity.builder()
                        .id(UUID.randomUUID())
                        .configDesc(input.getConfigName())
                        .configDesc(input.getConfigDesc())
                        .configValue(input.getConfigDefaultValue())
                .build());
    }
}
