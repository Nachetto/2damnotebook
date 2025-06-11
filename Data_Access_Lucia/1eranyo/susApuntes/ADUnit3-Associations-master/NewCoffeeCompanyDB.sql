drop table if exists coffees;
drop table if exists encriptedcodes;
drop table if exists suppliers;


create table suppliers
(
    SUPP_ID   integer NOT NULL,
    SUPP_NAME varchar(40),
    STREET    varchar(40),
    TOWN      varchar(20),
    COUNTRY   varchar(2),
    PCODE     varchar(5),
    PRIMARY KEY (SUPP_ID)
);

create table encriptedcodes
(

    id_coffee integer auto_increment not null,
    code  integer not null,
    PRIMARY KEY (id_coffee)
);

create table coffees
(
    id_prod  integer NOT NULL,
    COF_NAME varchar(32),
    SUPP_ID  int            NOT NULL,
    PRICE    numeric(10, 2) NOT NULL,
    SALES    integer        NOT NULL,
    TOTAL    integer        NOT NULL,
    PRIMARY KEY (id_prod),
    FOREIGN KEY (SUPP_ID) REFERENCES suppliers (SUPP_ID),
    constraint encriptedcodes_coffees_fk
        foreign key (id_prod) references encriptedcodes (id_coffee)
);




insert into suppliers
values (49, 'superior Coffee', '1 Party Place', 'Mendocino', 'CA', '95460');
insert into suppliers
values (101, 'Acme, Inc.', '99 market ST', 'Groundsville', 'CA', '95199');
insert into suppliers
values (150, 'The High Ground', '100 Coffee Lane', 'Meadows', 'CA', '93966');

INSERT INTO encriptedcodes (id_coffee, code)
VALUES (1, 100);
INSERT INTO encriptedcodes (id_coffee, code)
VALUES (2, 200);
INSERT INTO encriptedcodes (id_coffee, code)
VALUES (3, 300);
INSERT INTO encriptedcodes (id_coffee, code)
VALUES (4, 400);
INSERT INTO encriptedcodes (id_coffee,code)
VALUES (5, 500);

insert into coffees
values (1, 'Colombian', 101, 7.99, 0, 0);
insert into coffees
values (2, 'French_Roast', 49, 8.99, 0, 0);
insert into coffees
values (3, 'Espresso', 150, 9.99, 0, 0);
insert into coffees
values (4, 'Colombian_Decaf', 101, 8.99, 0, 0);
insert into coffees
values (5, 'French_Roast_Decaf', 49, 9.99, 0, 0);


