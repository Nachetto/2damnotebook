drop table if exists eucodes;
drop table if exists products;
drop table if exists coffees;
drop table if exists suppliers;

create table euCodes (FirstDig integer NOT NULL, SecondDig integer NOT NULL, ThirdDig integer NOT NULL, PRIMARY KEY (FirstDig));


create table products (id_prod integer NOT NULL, country varchar(15), FirstDig integer NOT NULL, PRIMARY KEY (id_prod));


create table suppliers (SUPP_ID integer NOT NULL, SUPP_NAME varchar(40), STREET varchar(40), TOWN varchar(20), COUNTRY varchar(2), PCODE varchar(5), PRIMARY KEY (SUPP_ID));


create table coffees (id_prod integer NOT NULL, COF_NAME varchar(32), SUPP_ID int NOT NULL, PRICE numeric(10,2) NOT NULL, SALES integer NOT NULL, TOTAL integer NOT NULL, PRIMARY KEY (id_prod), FOREIGN KEY (SUPP_ID) REFERENCES suppliers(SUPP_ID));

insert into eucodes values (101, 1, 1);
insert into eucodes values (201, 1, 1);
insert into eucodes values (301, 1, 1);

insert into suppliers values(49, 'superior Coffee', '1 Party Place', 'Mendocino', 'CA', '95460');
insert into suppliers values(101, 'Acme, Inc.', '99 market ST', 'Groundsville', 'CA', '95199');
insert into suppliers values(150, 'The High Ground', '100 Coffee Lane', 'Meadows', 'CA', '93966');

insert into coffees values(1, 'Colombian', 00101, 7.99, 0, 0);
insert into coffees values(2, 'French_Roast', 00049, 8.99, 0, 0);
insert into coffees values(3, 'Espresso', 00150, 9.99, 0, 0);
insert into coffees values(4, 'Colombian_Decaf', 00101, 8.99, 0, 0);
insert into coffees values(5, 'French_Roast_Decaf', 00049, 9.99, 0, 0);

insert into products values(1, "Spain", 101);
insert into products values(2, "Spain", 101);
insert into products values(3, "UK", 201);
insert into products values(4, "UK", 201);
insert into products values(5, "Canada", 301);
insert into products values(6, "USA", 301);
