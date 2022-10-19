drop table if exists user;
create table user (
    username VARCHAR(200) PRIMARY key,
    contrasenia VARCHAR(200) NOT NULL,
    nombre varchar(200) not null,
    apellido varchar(500) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    cargo VARCHAR(200) NOT NULL,
    activo BOOL NOT NULL
);
INSERT INTO user
(username, contrasenia, nombre, apellido, fecha_nacimiento,cargo,activo) values
    ('Chafo','xlchafoops1','Martin','Chafloque','2000/04/10','Administrador',TRUE),
	 ('Aleja2002','suarezm2002','Alejandra','Suarez','2002/08/20','Administrador',TRUE);