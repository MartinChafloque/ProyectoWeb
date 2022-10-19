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

    public List<Book> listAll(){
        return repo.findAll();
    }
    public Book save(Book book){
        return repo.save(book);
    }
    public Book get(Integer id){
        return repo.findById(id).get();
    }
    public void delete(Integer id){
        repo.deleteById(id);
    }

    public List<Book> byEditorialId(Integer editorialId){
        return repo.findByEditorial(editorialId);
    }

    public List<Book> filterByName(String name){
        return repo.findBookByName(name);
    }
}
