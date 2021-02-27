package com.plhal.ares.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("ares-api-properties")
public class AresApiProperties {

    private String urlPrefix;
    private String urlSufix;

}
