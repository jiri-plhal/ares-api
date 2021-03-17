package com.plhal.ares.parser;

import lombok.Data;

import java.net.URL;

/**
 * This class stores URL Properties for connection to Czech business register.
 */
@Data
public class ParserRepositoryProperties {

    private URL urlPrefix;
    private String urlSufix;
}
