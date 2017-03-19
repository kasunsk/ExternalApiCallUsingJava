CREATE DATABASE TravelPortal;

USE TravelPortal;

DROP TABLE USER IF EXISTS;
DROP TABLE BANK_ACCOUNT IF EXISTS;
DROP TABLE AIRLINE_OFFER IF EXISTS;
DROP TABLE USER_TICKET IF EXISTS;
DROP TABLE AIRPORTS IF EXISTS;
DROP TABLE EMAIL_DATA IF EXISTS;

CREATE TABLE USER (
    USER_ID INT NOT NULL auto_increment,
    NAME VARCHAR(50) NOT NULL,
    EMAIL VARCHAR(60) NOT NULL,
    PASSWORD VARCHAR(100) NOT NULL,
    USER_ROLE VARCHAR(10) NOT NULL,
    CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_DATE TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    VERSION INT(3),
    PRIMARY KEY (USER_ID)
);

INSERT INTO USER (USER_ID, NAME, EMAIL, PASSWORD, USER_ROLE, VERSION)  VALUES ('1', 'Admin','admin','LFG9iIrDdDL4vzV60SocZA==','ADMIN',0);

CREATE TABLE BANK_ACCOUNT (
    ID INT NOT NULL auto_increment,
    USER_ID INT(10) NOT NULL,
    CURRENCY VARCHAR(5) NOT NULL,
    AVAILABLE_AMOUNT INT(10) NOT NULL,
    CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_DATE TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    VERSION INT(3),
    PRIMARY KEY (ID),
    FOREIGN KEY (USER_ID)
    REFERENCES USER(USER_ID) ON DELETE CASCADE
);

CREATE TABLE AIRLINE_OFFER (
    OFFER_ID INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    ORIGIN VARCHAR(50) NOT NULL ,
    DESTINATION VARCHAR(50) NOT NULL,
    PRICE INT(10) NOT NULL,
    CURRENCY VARCHAR(5) NOT NULL,
    STATUS VARCHAR(10) NOT NULL,
    AVAILABLE_INV INT(10) NOT NULL,
    CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_DATE TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    VERSION INT(3)
);

INSERT INTO AIRLINE_OFFER VALUES (1,'SHJ', 'CMB', 100, 'USD', 'AVAILABLE', 200, '2017-03-17 00:33:02', NULL , 0);
INSERT INTO AIRLINE_OFFER VALUES (2,'CED', 'AER', 75, 'HKD', 'AVAILABLE', 250, '2017-03-17 00:33:02', NULL , 0);
INSERT INTO AIRLINE_OFFER VALUES (3,'AXA', 'AAU', 100, 'HUF', 'AVAILABLE', 100, '2017-03-17 00:33:02', NULL , 0);
INSERT INTO AIRLINE_OFFER VALUES (4,'CED', 'KUL', 100, 'USD', 'AVAILABLE', 50, '2017-03-17 00:33:02', NULL , 0);

CREATE TABLE USER_TICKET (
    ID INT NOT NULL auto_increment PRIMARY KEY,
    USER_ID INT(10) NOT NULL,
    OFFER_ID INT(10) NOT NULL ,
    ORIGIN VARCHAR(50) NOT NULL ,
    DESTINATION VARCHAR(50) NOT NULL,
    PRICE INT(10) NOT NULL,
    CURRENCY VARCHAR(5) NOT NULL,
    TICKETS_AMOUNT INT(5) NOT NULL,
    STATUS VARCHAR(10) NOT NULL,
    CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_DATE TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    VERSION INT(3)
);

CREATE TABLE EMAIL_DATA(
    EMAIL_ID INT NOT NULL auto_increment PRIMARY KEY,
    RECEIVER_MAIL VARCHAR(40) NOT NULL,
    SUBJECT VARCHAR(40) NOT NULL,
    CONTENT VARCHAR (250) NOT NULL,
    STATUS VARCHAR(10) NOT NULL,
    CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_DATE TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    VERSION INT(3)
);

CREATE TABLE AIRPORT (
    ID INT NOT NULL auto_increment PRIMARY KEY,
    AIR_PORT_CODE VARCHAR(10) NOT NULL,
    AIRPORT_NAME VARCHAR(40) NOT NULL,
    COUNTY VARCHAR(30) NOT NULL
);

insert into AIRPORT values (1,"SHJ","Shaja","UA");
insert into AIRPORT values (2,"CMB", "Katunayaka", "Sri Lanka");
insert into AIRPORT values (3,"KUL", "Kuwalalampur","Malesiya");

insert into AIRPORT values (4,"AAU","Anchorage","USA");
insert into AIRPORT values (5,"ANC", "Katunayaka", "Sri Lanka");
insert into AIRPORT values (6,"AOI", "Ancona","Italy");

insert into AIRPORT values (7,"SLU","St. Lucia Vigle","Saint Lucia");
insert into AIRPORT values (8,"SGF", "Springfield (MO)", "USA");
insert into AIRPORT values (9,"ALV", "Andorra La Vella - Heliport","Andorra");

insert into AIRPORT values (10,"AXA","Anguilla","Anguilla");
insert into AIRPORT values (11,"AAR", "Aarhus", "Denmark");
insert into AIRPORT values (12,"ABD", "Abadan","	Iran");

insert into AIRPORT values (13,"AER","Adler/Sochi","Russia");
insert into AIRPORT values (14,"AJY", "	Niger", "Agades");
insert into AIRPORT values (15,"DMK", "Bangkok, Don Muang","Thailand");

insert into AIRPORT values (16,"BKK","Bangkok, Suvarnabhumi International","Thailand");
insert into AIRPORT values (17,"CED", "Ceduna", "Australia");
insert into AIRPORT values (18,"DAB", "Daytona Beach (FL)","	USA");

insert into AIRPORT values (19,"YEG","Edmonton, International","Canada");
insert into AIRPORT values (20,"EBB", "Entebbe - Entebbe International Airport", "Uganda");
insert into AIRPORT values (21,"ERZ", "Erzurum","Turkey");
