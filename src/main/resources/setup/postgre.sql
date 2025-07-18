-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------

CREATE TABLE personen (
    id SERIAL PRIMARY KEY,
    familienaam VARCHAR(255),
    voornaam VARCHAR(255)
);

CREATE TABLE contactcodes (
    id CHAR(1) PRIMARY KEY,
    naam VARCHAR(255)
);

CREATE TABLE contactgegevens (
    id SERIAL PRIMARY KEY,
    p_id INTEGER,
    code CHAR(1),
    adres VARCHAR(255),
    CONSTRAINT fk_contactcodes FOREIGN KEY (code)
        REFERENCES contactcodes (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT fk_personen FOREIGN KEY (p_id)
        REFERENCES personen (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);