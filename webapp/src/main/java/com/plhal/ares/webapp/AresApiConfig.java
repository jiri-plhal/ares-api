package com.plhal.ares.webapp;

import io.swagger.client.ApiClient;
import io.swagger.client.api.WebapiControllerApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for webapp module.
 */
@Configuration
public class AresApiConfig {

    /**
     * Bean for setting ApiClient to webapi microservice
     * @param webapiProperties Object which contains url for connection to webapi microservice
     * @return Object of ApiClient
     */
    @Bean
    public ApiClient apiClient(WebapiProperties webapiProperties) {
        return new ApiClient().setBasePath(webapiProperties.getUrl());
    }

    /**
     * Bean for comunicating with webapi microservice using generating code frow swagger
     * @param apiClient ApiClient fot particular microservice
     * @return Object of WebapiControllerApi
     */
    @Bean
    public WebapiControllerApi webapiControllerApi(ApiClient apiClient) {
        return new WebapiControllerApi(apiClient);
    }

    /**
     * Bean for webapi microservice properties
     * @return Object of webapi properties
     */
    @Bean
    @ConfigurationProperties("webapi")
    public WebapiProperties webapiProperties() {
        return new WebapiProperties();
    }

}
