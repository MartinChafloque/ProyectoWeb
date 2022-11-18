package com.example.editorial.service;

import com.example.editorial.entity.Editorial;
import com.example.editorial.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
//Declaración de los métodos que contienen la lógica.
@Service
public class EditorialService {
    @Autowired
    private EditorialRepository repo;

    /** Obtiene la lista de editoriales
     *
     * @return la lista de editoriales
     */
    public List<Editorial> listAll(){
        return repo.findAll();
    }

    /** Guarda una nueva editorial
     *
     * @param editorial
     * @return la editorial
     */
    public Editorial save(Editorial editorial){
        return repo.save(editorial);
    }

    /** Obtiene una editorial dado un id
     *
     * @param id
     * @return la editorial encontrada
     */
    public Editorial get(Integer id){
        return repo.findById(id).get();
    }

    /** Elimina una editorial dado su id
     *
     * @param id
     */
    public void delete(Integer id){
        repo.deleteById(id);
    }


}
