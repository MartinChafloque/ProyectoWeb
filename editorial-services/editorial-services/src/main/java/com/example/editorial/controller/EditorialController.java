package com.example.editorial.controller;

import com.example.editorial.entity.Editorial;
import com.example.editorial.models.Book;
import com.example.editorial.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
@RestController
public class EditorialController {
    @Autowired
    private final EditorialService edService;

    public EditorialController(EditorialService edService) {
        this.edService = edService;
    }

    //RESTful API methods for retrieval operations
    @GetMapping("/editorials")//Return a list of all the books in the BD
    public List<Editorial> list(){
        return edService.listAll();
    } //check

    @GetMapping("/editorials/{id}")//Get information about a specific product based on ID
    public ResponseEntity<Editorial> get(@PathVariable Integer id){
        try{
            //if a product is found for the given ID, the server sends a response that includes JSON representation of
            // the Product object with HTTP status OK (200)
            Editorial ed= edService.get(id);
            return new ResponseEntity<Editorial>(ed, HttpStatus.OK);
        }catch(NoSuchElementException e){
            //if no product is found, it returns HTTP status Not Found (404).
            return new ResponseEntity<Editorial>(HttpStatus.NOT_FOUND);
        }
    } //check
    @PostMapping("/editorials")//Create a book
    public void add(@RequestBody Editorial ed){
        edService.save(ed);
    } //check
    //Update a book
    @PutMapping("/editorials/{id}") //Update a book given a specific id
    public ResponseEntity<?> update(@RequestBody Editorial editorial,@PathVariable Integer id){
        try{
            // If a product found with the given ID, it is updated and the server returns HTTP status OK.
            Editorial existEditorial= edService.get(id);
            existEditorial.setName(editorial.getName());
            System.out.println(editorial.getName());
            existEditorial.setWeb_site(editorial.getWeb_site());
            edService.save(existEditorial);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoSuchElementException e){
            //if no product found, the HTTP status Not Found (404) is returned.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/editorials/{id}")
    public ResponseEntity<?> patch(@PathVariable Integer id, @RequestBody Map<String,Object> fields){
        Editorial existEditorial= edService.get(id);
        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(Editorial.class,key);
            if(field !=null){
                field.setAccessible(true);
                ReflectionUtils.setField(field, existEditorial,value);
            }
        });
        Editorial result = edService.save(existEditorial);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/editorials/{id}")//Delete a book
    public void delete(@PathVariable Integer id){
        edService.delete(id);
    }

    @GetMapping("/editorials/books/{editorialId}")
    public ResponseEntity<List<Book>> getBooks(@PathVariable("editorialId") Integer id){
        Editorial ed = edService.get(id);
        if(ed == null){
            return ResponseEntity.notFound().build();
        }
        List<Book> books = edService.getBooks(id);
        return ResponseEntity.ok(books);

    }
}
