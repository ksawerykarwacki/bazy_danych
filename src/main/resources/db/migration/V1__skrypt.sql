CREATE SEQUENCE id_lokale_seq MINVALUE 1 MAXVALUE 100000 START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE SEQUENCE id_stanowiska_seq MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE SEQUENCE id_pracownicy_seq MINVALUE 1 MAXVALUE 10000000 START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE SEQUENCE id_skladniki_seq MINVALUE 1 MAXVALUE 10000 START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE SEQUENCE id_zamowienia_na_dowoz_seq MINVALUE 1 MAXVALUE 10000000 START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE SEQUENCE id_dania_seq MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE lokale (
    id                    NUMBER(5, 0),
    adres                 VARCHAR2(60) NOT NULL,
    miasto                VARCHAR2(60) NOT NULL,
    kod_pocztowy          VARCHAR2(6) NOT NULL,
    powierzchnia_w_m2     NUMBER(4, 2) NOT NULL,
    miejsca_dla_klientow  NUMBER(4, 0) NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE stanowiska (
    id             NUMBER(3, 0),
    stanowisko     VARCHAR2(30) UNIQUE NOT NULL,
    wynagrodzenie  NUMBER(6, 2) NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE pracownicy (
    id                 NUMBER(7, 0),
    imie               VARCHAR2(30) NOT NULL,
    nazwisko           VARCHAR2(30) NOT NULL,
    lokal_id              NUMBER(5, 0) NOT NULL,
    stanowisko_id         NUMBER(3, 0) NOT NULL,
    data_zatrudnienia  DATE NOT NULL,
    PRIMARY KEY ( id ),
    FOREIGN KEY ( lokal_id )
        REFERENCES lokale ( id )
            ON DELETE CASCADE,
    FOREIGN KEY ( stanowisko_id )
        REFERENCES stanowiska ( id )
            ON DELETE CASCADE
);

CREATE TABLE skladniki (
    id             NUMBER(4, 0),
    skladnik       VARCHAR2(50) UNIQUE NOT NULL,
    jednostka      VARCHAR2(20) NOT NULL,
    wegetarianski  VARCHAR2(1) NOT NULL,
    bezglutenowy   VARCHAR2(1) NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE zapasy (
    lokal     NUMBER(5, 0),
    skladnik  NUMBER(4, 0),
    ilosc     NUMBER(4, 2) NOT NULL,
    PRIMARY KEY ( lokal,
                  skladnik ),
    FOREIGN KEY ( lokal )
        REFERENCES lokale ( id )
            ON DELETE CASCADE,
    FOREIGN KEY ( skladnik )
        REFERENCES skladniki ( id )
            ON DELETE CASCADE
);

CREATE TABLE zamowienia_na_dowoz (
    id                NUMBER(7, 0),
    adres             VARCHAR2(60) NOT NULL,
    miasto            VARCHAR2(60) NOT NULL,
    najblizszy_lokal  NUMBER(5, 0) NOT NULL,
    dostawca          NUMBER(7, 0) NOT NULL,
    PRIMARY KEY ( id ),
    FOREIGN KEY ( najblizszy_lokal )
        REFERENCES lokale ( id )
            ON DELETE CASCADE,
    FOREIGN KEY ( dostawca )
        REFERENCES pracownicy ( id )
            ON DELETE CASCADE
);

CREATE TABLE dania (
    id     NUMBER(3, 0),
    danie  VARCHAR2(50) UNIQUE NOT NULL,
    cena   NUMBER(5, 2) NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE zamowione_jedzenie (
    zamowienie  NUMBER(7, 0),
    danie       NUMBER(3, 0),
    ilosc       NUMBER(2, 0),
    PRIMARY KEY ( zamowienie,
                  danie ),
    FOREIGN KEY ( zamowienie )
        REFERENCES zamowienia_na_dowoz ( id )
            ON DELETE CASCADE,
    FOREIGN KEY ( danie )
        REFERENCES dania ( id )
            ON DELETE CASCADE
);

CREATE TABLE sklad_dan (
    danie     NUMBER(3, 0),
    skladnik  NUMBER(4, 0),
    PRIMARY KEY ( danie,
                  skladnik ),
    FOREIGN KEY ( danie )
        REFERENCES dania ( id )
            ON DELETE CASCADE,
    FOREIGN KEY ( skladnik )
        REFERENCES skladniki ( id )
            ON DELETE CASCADE
);

CREATE TABLE dostawcy_skladnikow (
    skladnik  NUMBER(4, 0),
    dostawca  VARCHAR2(50),
    cena      NUMBER(4, 0) NOT NULL,
    PRIMARY KEY ( skladnik,
                  dostawca ),
    FOREIGN KEY ( skladnik )
        REFERENCES skladniki ( id )
            ON DELETE CASCADE
);

CREATE OR REPLACE TYPE skladniki_lista IS 
    VARRAY(20) OF NUMBER(4,0);
/

CREATE OR REPLACE TYPE dania_lista IS
    VARRAY(20) OF NUMBER(3,0);
/

CREATE OR REPLACE TYPE ilosci_lista IS
    VARRAY(20) OF NUMBER(2,0);
/

Create or replace PROCEDURE wstaw_zamowienie_na_dowoz (
    adres             IN  zamowienia_na_dowoz.adres%TYPE,
    miasto            IN  zamowienia_na_dowoz.miasto%TYPE,
    najblizszy_lokal  IN  zamowienia_na_dowoz.najblizszy_lokal%TYPE,
    dostawca          IN  zamowienia_na_dowoz.dostawca%TYPE,
    dania             IN  dania_lista,
    ilosc             IN  ilosci_lista
) 
IS
BEGIN
    INSERT INTO zamowienia_na_dowoz (
        id,
        adres,
        miasto,
        najblizszy_lokal,
        dostawca
    ) VALUES (
        id_zamowienia_na_dowoz_seq.NEXTVAL,
        adres,
        miasto,
        najblizszy_lokal,
        dostawca
    );

    FOR i IN 1..dania.count LOOP
        INSERT INTO zamowione_jedzenie (
            zamowienie,
            danie,
            ilosc
        ) VALUES (
            id_zamowienia_na_dowoz_seq.CURRVAL,
            dania(i),
            ilosc(i)
        );

    END LOOP;

END;
/

Create or replace PROCEDURE wstaw_danie (
    danie     IN  dania.danie%TYPE,
    cena      IN  dania.cena%TYPE,
    skladnik  IN  skladniki_lista
) AS
BEGIN
    INSERT INTO dania (
        id,
        danie,
        cena
    ) VALUES (
        id_dania_seq.NEXTVAL,
        danie,
        cena
    );

    FOR i IN 1..skladnik.count LOOP
        INSERT INTO sklad_dan (
            danie,
            skladnik
        ) VALUES (
            id_dania_seq.CURRVAL,
            skladnik(i)
        );

    END LOOP;

END;
/

CREATE TRIGGER pracownik_ustaw_id
  BEFORE INSERT ON pracownicy
  FOR EACH ROW
DECLARE
BEGIN
  IF( :new.id IS NULL )
  THEN
    :new.id := id_pracownicy_seq.nextval;
  END IF;
END;
/
