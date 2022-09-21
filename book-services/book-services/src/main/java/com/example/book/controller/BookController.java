package com.example.book.controller;

import com.example.book.entity.Book;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class BookController {
    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //RESTful API methods for retrieval operations
    @GetMapping("/books")//Return a list of all the books in the BD
    public List<Book> list(){
        return bookService.listAll();
    }

    @GetMapping("/books/{id}")//Get information about a specific product based on ID
    public ResponseEntity<Book> get(@PathVariable Integer id){
        try{
            //if a product is found for the given ID, the server sends a response that includes JSON representation of
            // the Product object with HTTP status OK (200)
            Book book= bookService.get(id);
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        }catch(NoSuchElementException e){
            //if no product is found, it returns HTTP status Not Found (404).
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")//Create a book
    public void add(@RequestBody Book book){
        bookService.save(book);
    } //check

    //Update a book
    @PutMapping("/books/{id}") //Update a book given a specific id
    public ResponseEntity<?> update(@RequestBody Book book,@PathVariable Integer id){
        try{
            // If a product found with the given ID, it is updated and the server0returns HTTP status OK.
            Book existBook= bookService.get(id);
            //System.out.println(existBook);
            existBook.setName(book.getName());
            existBook.setDescription(book.getDescription());
            existBook.setImageUrl(book.getImageUrl());
            existBook.setCantidad(book.getCantidad());
            existBook.setEditorialId(book.getEditorialId());
            bookService.save(existBook);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoSuchElementException e){
            //if no product found, the HTTP status Not Found (404) is returned.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/books/{id}")//Update a book given a specific id
    public ResponseEntity<?> patch(@PathVariable Integer id, @RequestBody Map<String,Object> fields){
        Book existingBook = bookService.get(id);
        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(Book.class,key);
            if(field !=null){
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingBook,value);
            }
        });
        Book result = bookService.save(existingBook);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/books/{id}")//Delete a book
    public void delete(@PathVariable Integer id){
        bookService.delete(id);
    }

    @GetMapping("/books/editorial/{editorialId}") //Gets a list of books given an editorial
    public ResponseEntity<List<Book>> listBooksByEditorialId(@PathVariable("editorialId") Integer id){
        List<Book> books = bookService.byEditorialId(id);
        if(books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }
    @Modifying
    @GetMapping("/book/{name}") //Filter Books by a name.
    public ResponseEntity<List<Book>> filterByName(@PathVariable("name") String name){
        //System.out.println(name);
        List<Book> books= bookService.filterByName("%" + name + "%");
        if(books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }
}
