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
//Clase donde se implementan los métodos declarados por el servicio

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BookController {
    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //Métodos API RESTful para operaciones de recuperación

    //Devuelve una lista de todos los libros que estan en la BD
    @GetMapping("/books")
    public List<Book> list(){
        return bookService.listAll();
    }

    //Obtener información sobre un libro específico basado en un id
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> get(@PathVariable Integer id){
        try{
            //si se encuentra una editorial para el ID dado, el servidor envía una respuesta que incluye
            // la representación JSON del objeto con el estado HTTP OK (200)
            Book book= bookService.get(id);
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        }catch(NoSuchElementException e){
            //si no se encuentra ningún producto, devuelve el estado HTTP status Not Found (404).
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
    }

    //Crea un libro dado los atributos necesarios para crearlo.
    @PostMapping("/books")
    public void add(@RequestBody Book book){
        bookService.save(book);
    } //check

    //Actualiza un libro dado un id
    @PutMapping("/books/{id}")
    public ResponseEntity<?> update(@RequestBody Book book,@PathVariable Integer id){
        try{
            // Si se encuentra una editorial con el id dado, se actualiza y el servidor devuelve el estado HTTP OK.
            Book existBook= bookService.get(id);
            existBook.setName(book.getName());
            existBook.setDescription(book.getDescription());
            existBook.setImageUrl(book.getImageUrl());
            existBook.setCantidad(book.getCantidad());
            existBook.setEditorialId(book.getEditorialId());
            bookService.save(existBook);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoSuchElementException e){
            //si no se encuentra ningun libro, se devuelve el estado HTTP Not found (404).
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Actualiza un libro dado un id, con el método patch, esto para que no tenga que pasar todos los atributos de la editorial
    //si no solo el atributo que se quiere actualizar.
    @PatchMapping("/books/{id}")
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

    //Elimina un libro dado su id.
    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable Integer id){
        bookService.delete(id);
    }

    //Obtiene una lista de libros dado un editorial
    @GetMapping("/books/editorial/{editorialId}")
    public ResponseEntity<List<Book>> listBooksByEditorialId(@PathVariable("editorialId") Integer id){
        List<Book> books = bookService.byEditorialId(id);
        if(books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }
    //Filtrar libros por nombre.
    @Modifying
    @GetMapping("/book/{name}") //Filter Books by a name.
    public ResponseEntity<List<Book>> filterByName(@PathVariable("name") String name){
        List<Book> books= bookService.filterByName("%" + name + "%");
        if(books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }
}
