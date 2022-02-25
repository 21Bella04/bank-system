DROP SCHEMA IF EXISTS `bank`;
CREATE SCHEMA `bank`;

-- create ADDRESS
CREATE TABLE BANK.ADDRESS
(
    ID           int NOT NULL AUTO_INCREMENT,
    STREET       varchar(255),
    HOUSE_NUMBER varchar(255),
    CITY         varchar(255),
    COUNTRY      varchar(255),
    PRIMARY KEY (ID)
);

-- create CUSTOMER
CREATE TABLE BANK.CUSTOMER
(
    ID           int NOT NULL AUTO_INCREMENT,
    NAME         varchar(255),
    SURNAME      varchar(255),
    ADDRESS_FK   int not null,
    PHONE_NUMBER varchar(255),
    EMAIL        varchar(255),
    PRIMARY KEY (ID),
    FOREIGN KEY (ADDRESS_FK) REFERENCES ADDRESS (ID) ON DELETE CASCADE
);


-- create ACCOUNT
CREATE TABLE BANK.ACCOUNT
(
    ID                  int          NOT NULL AUTO_INCREMENT,
    CUSTOMER_FK         int          NOT NULL,
    BANK_ACCOUNT_NUMBER varchar(8)   NOT NULL,
    BALANCE             DECIMAL(19, 2)        DEFAULT 0,
    LOGIN               varchar(255) NOT NULL,
    PASSWORD            varchar(255) NOT NULL,
    ADMIN               boolean      NOT NULL DEFAULT false,
    PRIMARY KEY (ID),
    FOREIGN KEY (CUSTOMER_FK) REFERENCES CUSTOMER (ID) ON DELETE CASCADE,
    CONSTRAINT ACCOUNT_CUSTOMER_FK FOREIGN KEY (CUSTOMER_FK) REFERENCES CUSTOMER (ID) ON DELETE CASCADE

);

-- create TRANSACTION
CREATE TABLE BANK.TRANSACTION
(
    ID              int                      NOT NULL AUTO_INCREMENT,
    DATE            timestamp                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    TYPE            enum ('SUPPLY', 'DEBIT') NOT NULL,
    AMOUNT          DECIMAL(19, 2)           NOT NULL,
    ACCOUNT_FK int                      NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (ACCOUNT_FK) REFERENCES ACCOUNT (ID) ON DELETE CASCADE
);
CREATE VIEW bank.bank_capital AS
SELECT SUM(BALANCE) AS capital_balance, AVG(BALANCE) AS average_balance
from BANK.ACCOUNT;

SET FOREIGN_KEY_CHECKS = 0;
# TODO 3. You should make use of Aggregation and/or Grouping
# TODO 4. Create and use a View

SET FOREIGN_KEY_CHECKS = 0;
