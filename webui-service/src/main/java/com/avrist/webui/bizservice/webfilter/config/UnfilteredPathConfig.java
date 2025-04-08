package com.avrist.webui.bizservice.webfilter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "webconfig.filter.unfiltered")
public class UnfilteredPathConfig {
    private List<String> path;
}
