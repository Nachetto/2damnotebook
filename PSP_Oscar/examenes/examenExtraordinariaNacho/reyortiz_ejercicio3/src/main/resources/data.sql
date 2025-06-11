delete from moviles;
delete from empleados;

insert into empleados (id, nombre, password, fecha_nac,rol) values (1, 'admin', '$2a$10$YPRZcNVYJl2aAUOaZJd3burUSJmfZRX8ye7Ciq1NUUFunMiHh9xOS', '1997-02-01',"ADMIN");
insert into empleados (id, nombre, password, fecha_nac,rol) values (2, 'test1', '$2a$10$OnkTCIaCMVQ9e3NeCsX0T.wxIjMoVIG8hEZeIbZwQBhb0kfJps8su', '1980-03-10',"EMPLEADO");
insert into empleados (id, nombre, password, fecha_nac,rol) values (3, 'test2', '$2a$10$OnkTCIaCMVQ9e3NeCsX0T.wxIjMoVIG8hEZeIbZwQBhb0kfJps8su', '1999-04-05',"EMPLEADO");

-- contraseña para admin -> admin
-- contraseñas para test1, test2 -> test

insert into moviles (id, capacidad, modelo, year, empleado) values (1, 40, "Pixel4a", 2019, 2);
insert into moviles (id, capacidad, modelo, year, empleado) values (2, 70, "MotorolaX", 2008, 3);
insert into moviles (id, capacidad, modelo, year, empleado) values (3, 80, "Samsung Galaxy", 2018, 2);
insert into moviles (id, capacidad, modelo, year, empleado) values (4, 90, "Htc", 2015, 3);






















