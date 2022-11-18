package com.example.book.service;

import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Declaración de los métodos que contienen la lógica.
@Service
public class BookService {
    @Autowired
    private BookRepository repo;

    /** Lista todos los libros
     *
     * @return la lista de libros
     */
    public List<Book> listAll(){
        return repo.findAll();
    }

    /** Guarda un libro
     *
     * @param book
     * @return el libro
     */
    public Book save(Book book){
        return repo.save(book);
    }

    /** Busca un libro dado un id
     *
     * @param id
     * @return el libro
     */
    public Book get(Integer id){
        return repo.findById(id).get();
    }

    /** Elimina un libro dado un id
     *
     * @param id
     */
    public void delete(Integer id){
        repo.deleteById(id);
    }

    /** Lista los libros dado un id de editorial
     *
     * @param editorialId
     * @return la lista de libros
     */
    public List<Book> byEditorialId(Integer editorialId){
        return repo.findByEditorial(editorialId);
    }

    /** Filtra los libros dado un nombre
     *
     * @param name
     * @return la lista filtrada de libros
     */
    public List<Book> filterByName(String name){
        return repo.findBookByName(name);
    }
}
