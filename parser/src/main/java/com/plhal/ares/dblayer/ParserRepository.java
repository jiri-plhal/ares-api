package com.plhal.ares.dblayer;


/**
 * Interface for getting informations about company
 */

public interface ParserRepository {
    /**
     * This method finds informations about company from Czech business register.
     *
     * @param ico Identification number of company.
     * @return Object of company with its informations. If company is not found or error happened, return value is null.
     */
    public Firma najdiFirmu(String ico);

}
