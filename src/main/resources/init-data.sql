INSERT INTO bank.ADDRESS (STREET, HOUSE_NUMBER, CITY, COUNTRY)
VALUES ('Example', '15', 'Lublin', 'Poland'),
       ('Street', '27/3', 'Warsaw', 'Poland'),
       ('Test', '11', 'Krakov', 'Poland'),
       ('Weird', '2', 'Lublin', 'Poland');

INSERT INTO bank.customer (NAME, SURNAME, ADDRESS_FK, PHONE_NUMBER, EMAIL)
VALUES ('Adam', 'Smith', 1, '073222222', 'adam_s@hotmail.com'),
       ('Johan', 'Blue', 2, '076789242', 'johanblue@hotmail.com'),
       ('Alicia', 'Green', 3, '073457890', 'aliciaaa@hotmail.com'),
       ('Matina', 'Grovak', 4, '073987651', 'govakmartina@hotmail.com'),
       ('Diana', 'Gill', 1, '073001298', 'dianaa@hotmail.com'),
       ('Marek', 'Rak', 2, '0604192846', 'marek_rak@hotmail.com'),
       ('Jane', 'Smith', 3, '073789534', 'smith_jane@hotmail.com'),
       ('Willy', 'Wonka', 4, '073546891', 'willychocolate@hotmail.com'),
       ('Adam', 'Brown', 1, '073589102', 'adamadam@hotmail.com'),
       ('Helena', 'Brown', 2, '073456700', 'helena_brown@hotmail.com'),
       ('Luisa', 'Svensson', 3, '073400961', 'svensson_l@hotmail.com'),
       ('Jack', 'Daniels', 4, '073765391', 'jacky@hotmail.com'),
       ('Camilla', 'Grey', 1, '073789551', 'grey_camilla@hotmail.com'),
       ('Rohan', 'Red', 2, '073455994', 'rohanred@hotmail.com');


INSERT INTO bank.account (CUSTOMER_FK, BANK_ACCOUNT_NUMBER, BALANCE, LOGIN, PASSWORD, ADMIN)
VALUES (1, '01234567', '-5000', 'admin', 'admin', true),
       (1, '78901234', '10000', 'smith123', 'smith123', false),
       (2, '77799081', '1000000', 'blue', 'blue', false),
       (2, '11111111', '-1000', 'blue1', 'blue1', false),
       (3, '01234444', '5000000000', 'green123', 'green123', false),
       (4, '00123456', '10000999', 'matina', 'matina', false),
       (4, '00119922', '1000569', 'matinaaa', 'matinaaa', false),
       (5, '99887735', '1000000', 'diana000', 'diana000', false),
       (6, '56748992', '888000', 'marekrak', 'marekrak', false),
       (6, '11299445', '5000', 'marekrak1', 'marekrak1', false),
       (7, '77786456', '99', 'smithjane', 'smithjane', false),
       (7, '73582930', '-1560', 'smithjane123', 'smithjane123', false),
       (8, '74455309', '100000000000', 'wonka', 'wonka', false),
       (9, '99966897', '5000888', 'brown1', 'brown1', false),
       (9, '99995427', '100045670', 'brown123', 'brown123', false),
       (10, '77712300', '10000', 'brown2', 'brown2', false),
       (11, '11239999', '-500990', 'svensson', 'svensson', false),
       (12, '75643599', '1000', 'j.daniels', 'j.daniels', false),
       (13, '11167835', '50000000', 'grey002', 'grey002', false),
       (13, '11120095', '10000', 'grey001', 'grey001', false),
       (14, '62899934', '1000', 'redrohan', 'redrohan', false);


INSERT INTO bank.transaction (type, amount, ACCOUNT_FK)
VALUES ('SUPPLY', '500', 1),
       ('DEBIT', '-500', 2),
       ('SUPPLY', '500', 4),
       ('DEBIT', '-500', 8),
       ('SUPPLY', '1500', 7),
       ('DEBIT', '-1500', 3),
       ('SUPPLY', '15000000', 14),
       ('DEBIT', '-15000000', 10),
       ('SUPPLY', '5000', 12),
       ('DEBIT', '-5000', 3),
       ('SUPPLY', '1500', 8),
       ('DEBIT', '-1500', 13),
       ('SUPPLY', '100', 10),
       ('DEBIT', '-100', 6),
       ('SUPPLY', '1500', 4),
       ('DEBIT', '-1500', 7),
       ('SUPPLY', '10000', 1),
       ('DEBIT', '-10000', 13),
       ('SUPPLY', '1500', 9),
       ('DEBIT', '-1500', 5);