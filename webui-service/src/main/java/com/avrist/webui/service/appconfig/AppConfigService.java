package com.avrist.webui.service.appconfig;

import com.avrist.webui.datasource.entity.AppConfigurationEntity;
import com.avrist.webui.global.exception.GeneralServiceValidationException;
import com.avrist.webui.service.appconfig.model.input.AppConfigInput;

public interface AppConfigService {
    AppConfigurationEntity load(AppConfigInput input) throws GeneralServiceValidationException;
}
