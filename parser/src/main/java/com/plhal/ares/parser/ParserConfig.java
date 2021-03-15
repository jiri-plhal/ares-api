package com.plhal.ares.parser;

import org.springframework.context.annotation.Bean;

public class ParserConfig {

    @Bean
    public ParserRepositoryProperties dataRepositoryProperties() {
        return new ParserRepositoryProperties();
    }

    @Bean
    public ParserRepository dataRepository(ParserRepositoryProperties parserRepositoryProperties) {
        return new ParserRepositoryImpl(parserRepositoryProperties);
    }

}
