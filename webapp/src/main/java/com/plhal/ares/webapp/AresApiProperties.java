package com.plhal.ares.webapp;

import com.plhal.ares.parser.ParserRepositoryProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@AllArgsConstructor
public class AresApiProperties {

    @NestedConfigurationProperty
    private final ParserRepositoryProperties parserRepositoryProperties;

}
