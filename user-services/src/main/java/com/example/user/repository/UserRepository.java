package com.example.user.repository;

import com.example.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE nombre like %:nombre%", nativeQuery = true)
    List<User> findUserByName(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User findUserByUsername(@Param("username") String username);

}