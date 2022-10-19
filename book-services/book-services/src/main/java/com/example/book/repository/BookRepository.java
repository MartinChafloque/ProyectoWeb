package com.example.book.repository;

import com.example.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
//Clase que extienden de JPARepository para que se conecten a la base de datos de libros.
public interface  BookRepository extends JpaRepository<Book, Integer> {
    //Query para obtener una lista de libros dado el id  de una editorial
    @Query(value = "SELECT * FROM  book WHERE  book.editorial_id = :editorialId", nativeQuery = true)
    public List<Book> findByEditorial(@Param("editorialId")Integer editorialId);

    //Query para obtener una lista de libros dado un su titulo
    @Query(value = "SELECT * FROM book WHERE name like %:name%", nativeQuery = true)
    public List<Book> findBookByName(@Param("name") String name);
}
