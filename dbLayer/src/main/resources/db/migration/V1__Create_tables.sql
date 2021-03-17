CREATE SCHEMA IF NOT EXISTS ares;

CREATE TABLE ares.firma (
    ico CHAR(8) PRIMARY KEY,
    nazev_firmy VARCHAR(100) NOT NULL,
    pravni_forma VARCHAR(100) NOT NULL,
    zakladni_kapital VARCHAR(100),
    sidlo VARCHAR(50) NOT NULL
    );

CREATE TABLE ares.predmet_podnikani (
    nazev VARCHAR(2000) PRIMARY KEY
    );

CREATE TABLE ares.statutarni_organ (
    statutarni_organ_id SERIAL PRIMARY KEY,
    firma_ico CHAR(8) REFERENCES ares.firma (ico),
    funkce VARCHAR(100) NOT NULL,
    jmeno VARCHAR(50) NOT NULL,
    prijmeni VARCHAR(50) NOT NULL
    );

CREATE TABLE ares.firma_predmet_podnikani (
    firma_predmet_podnikani_id SERIAL PRIMARY KEY,
    ico CHAR(8) REFERENCES ares.firma(ico),
    predmet_podnikani_nazev VARCHAR(2000) REFERENCES ares.predmet_podnikani (nazev)
    );
