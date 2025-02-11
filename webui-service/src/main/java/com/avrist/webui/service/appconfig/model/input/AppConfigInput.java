package com.avrist.webui.service.appconfig.model.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppConfigInput {
    private String configName;
    private String configDesc;
    private String configDefaultValue;
}
