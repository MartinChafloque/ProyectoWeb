package com.example.user.repository;

import com.example.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
//Clase que extienden de JPARepository para que se conecten a la base de datos de usuarios.
public interface  UserRepository extends JpaRepository<User,String> {
    //Query para filtrar por nombre
    @Query(value = "SELECT * FROM user WHERE nombre like %:nombre%", nativeQuery = true)
    List<User> findUserByName(@Param("nombre") String nombre);

}