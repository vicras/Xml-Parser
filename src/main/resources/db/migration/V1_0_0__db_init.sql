CREATE SEQUENCE IF NOT EXISTS SEQ_TENDER START 1;

CREATE TABLE IF NOT EXISTS EDI_DC40
(
    DOCNUM                    VARCHAR(255),
    TABNAM                    VARCHAR(255),
    MANDT                     VARCHAR(255),
    DOCREL                    VARCHAR(255),
    STATUS                    VARCHAR(255),
    DIRECT                    VARCHAR(255),
    OUTMOD                    VARCHAR(255),
    IDOCTYP                   VARCHAR(255),
    MESTYP                    VARCHAR(255),
    SNDPOR                    VARCHAR(255),
    SNDPRT                    VARCHAR(255),
    SNDPRN                    VARCHAR(255),
    RCVPOR                    VARCHAR(255),
    RCVPRT                    VARCHAR(255),
    CREDAT                    VARCHAR(255),
    CRETIM                    VARCHAR(255),
    SERIAL                    VARCHAR(255),
    RCVPRN                    VARCHAR(255),
    CONSTRAINT PK_EDI_DC40 PRIMARY KEY (DOCNUM)
);

CREATE TABLE IF NOT EXISTS E1WPA01
(
    FILIALE                 VARCHAR(255),
    AENDKENNZ               VARCHAR(255),
    AKTIVDATUM              VARCHAR(255),
    AENDDATUM               VARCHAR(255),
    HAUPTEAN                VARCHAR(255),
    ARTIKELNR               VARCHAR(255),
    POSME                   VARCHAR(255),

    E1WPA02_WARENGR                 VARCHAR(255),
    E1WPA02_VERPGEW                 VARCHAR(255),
    E1WPA02_RABERLAUBT              VARCHAR(255),
    E1WPA02_PRDRUCK                 VARCHAR(255),
    E1WPA02_ARTIKANZ                VARCHAR(255),
    E1WPA02_MHDHB                   BIGINT,

    E1WPA03_QUALARTTXT             VARCHAR(255),
    E1WPA03_SPRASCODE              VARCHAR(255),
    E1WPA03_TEXT                   VARCHAR(255),
    E1WPA03_LFDNR                  VARCHAR(255),

    CONSTRAINT PK_E1WPA01 PRIMARY KEY (FILIALE)
);


ALTER TABLE EDI_DC40
    ADD CONSTRAINT FK_E1WPA01_E1WPA01 FOREIGN KEY (RCVPRN) REFERENCES E1WPA01 (FILIALE);

CREATE INDEX index_name
    ON E1WPA01 (E1WPA02_MHDHB);