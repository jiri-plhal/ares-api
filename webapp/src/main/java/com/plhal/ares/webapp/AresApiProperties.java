package com.plhal.ares.webapp;

import com.plhal.ares.dbLayer.DataRepositoryProperties;
import lombok.Getter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
public class AresApiProperties {

    @NestedConfigurationProperty
    private DataRepositoryProperties dataRepositoryProperties;

    public AresApiProperties(DataRepositoryProperties dataRepositoryProperties) {
        this.dataRepositoryProperties = dataRepositoryProperties;
    }

}
