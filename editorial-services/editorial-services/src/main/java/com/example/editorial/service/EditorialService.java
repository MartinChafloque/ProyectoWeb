package com.example.editorial.service;

import com.example.editorial.entity.Editorial;
import com.example.editorial.models.Book;
import com.example.editorial.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EditorialService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EditorialRepository repo;

    public List<Editorial> listAll(){
        return repo.findAll();
    }
    public Editorial save(Editorial editorial){
        return repo.save(editorial);
    }
    public Editorial get(Integer id){
        return repo.findById(id).get();
    }
    public void delete(Integer id){
        repo.deleteById(id);
    }

    //Get a list book of a specific editorial
    public List<Book> getBooks(Integer editorialId){
        List<Book> books = restTemplate.getForObject("http://localhost:8080/books/editorial/"+ editorialId, List.class);
        return books;
    }
}
