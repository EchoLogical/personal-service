package com.avrist.webui.endpoint.logger.service.appconfig;

import com.avrist.webui.datasource.webapp.entity.AppConfigurationEntity;
import com.avrist.webui.global.exception.GeneralServiceValidationException;
import com.avrist.webui.endpoint.logger.service.appconfig.model.input.AppConfigInput;

public interface AppConfigService {
    AppConfigurationEntity load(AppConfigInput input) throws GeneralServiceValidationException;
}
