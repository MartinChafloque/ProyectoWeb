### Proyecto Desarrollo Web

Objetivo: Construcción de un sitio Web, donde se pueda 
publicitar los diferentes títulos con los que cuenta la librería, mostrando la portada 
del libro, el nombre, prólogo o introducción, editorial, fecha de edición y cantidad
disponible. Dicho contenido debe estar abierto a todo público, pero la administración 
como tal de los títulos a incluir debe ser exclusiva de los trabajadores de la librería.

## Requerimiento No Funcionales:
- Autenticación y Autorización: El módulo de administración de libros y 
usuarios (Consulta, Creación, Modificación, Eliminación) debe ser accedido 
únicamente por usuarios registrados y autenticados en la aplicación

## Requerimientos Funcionales:
- Módulo para gestión de usuarios (Listar, Crear, Modificar,
Inactivar). Para los usuarios se debe contemplar la identificación, nombres y
apellidos, fecha de nacimiento y cargo.

- Módulo para gestión de libros (Listar, Crear, Modificar, Eliminar).

- Vista principal que puede ser accedida por todo público donde se muestren
los títulos disponibles, adicionalmente se pueda realizar una búsqueda por
nombre y aplicar un filtro de búsqueda por editorial.

## Restricciones Técnicas:
En este proyecto se está utilizando: 
- SpringBoot versión 2.7.0.
- Bases de Datos SQL.
## Primera Entrega:
- Gestión de Usuarios y  Libros expuestos a través de Rest Services

# Microservicios utilizados:
Nota: Cada mircoservicio tiene una base de datos diferente(menos el mircroservicio de autenticación que utiliza la misma del microservicio de usuarios).
- **book-services:**
Este microservicio se encarga de gestionar los libros. 
Conexión a la Base de Datos: libros, tabla "book".
- **editorial-services:**
Este microservicio se encarga de gestionar de editoriales. 
Conexión a la Base de Datos: editoriales, tabla "editorial".
- **user-services:**
Este microservicio se encarga de gestionar los usuarios. 
Conexión a la Base de Datos: user, tabla "user".
- **authentication-services:**
Este microservicio se encarga de la autenticación de usuarios. 
Conexión a la Base de Datos: user, tabla "user".

