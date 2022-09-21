drop table if exists user;
create table user (
    id int primary key auto_increment,
    username VARCHAR(200) NOT NULL,
    contrasenia VARCHAR(200) NOT NULL,
    nombre varchar(200) not null,
    apellido varchar(500) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    cargo VARCHAR(200) NOT NULL,
    activo BOOL NOT NULL
);