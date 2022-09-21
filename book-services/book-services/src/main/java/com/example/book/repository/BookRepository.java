package com.example.book.repository;

import com.example.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT * FROM  book WHERE  book.editorial_id = :editorialId", nativeQuery = true)
    public List<Book> findByEditorial(@Param("editorialId")Integer editorialId); //Obtiene una lista de libros dado el id  de una editorial

    @Query(value = "SELECT * FROM book WHERE name like %:name%", nativeQuery = true)
    public List<Book> findBookByName(@Param("name") String name);
}
