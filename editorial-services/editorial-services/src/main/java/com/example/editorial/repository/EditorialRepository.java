package com.example.editorial.repository;

import com.example.editorial.entity.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Clase que extienden de JPARepository para que se conecten a la base de datos de editoriales.
@Repository
public interface EditorialRepository extends JpaRepository<Editorial,Integer> {
}
