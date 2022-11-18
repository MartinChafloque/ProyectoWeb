package com.example.editorial.controller;

import com.example.editorial.entity.Editorial;
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
//Clase donde se implementan los métodos declarados en el servicio
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EditorialController {
    @Autowired
    private final EditorialService edService;

    public EditorialController(EditorialService edService) {
        this.edService = edService;
    }


    /** Devuelta una lista de todas las editoriales en la BD
     *
     * @return la lista de editoriales
     */
    @GetMapping("/editorials")
    public List<Editorial> list(){
        return edService.listAll();
    }

    /** Obtiene información sobre una editorial basado en un id
     *
     * @param id
     * @return la editorial encontrada
     */
    @GetMapping("/editorials/{id}")
    public ResponseEntity<Editorial> get(@PathVariable Integer id){
        try{
            //si se encuentra una editorial para el ID dado, el servidor envía una respuesta que incluye
            // la representación JSON del objeto con el estado HTTP OK (200)
            Editorial ed= edService.get(id);
            return new ResponseEntity<Editorial>(ed, HttpStatus.OK);
        }catch(NoSuchElementException e){
            //si no se encuentra ningún producto, devuelve el estado HTTP status Not Found (404).
            return new ResponseEntity<Editorial>(HttpStatus.NOT_FOUND);
        }
    }

    /** Crea una nueva editorial
     *
     * @param ed
     */
    @PostMapping("/editorials")
    public void add(@RequestBody Editorial ed){
        edService.save(ed);
    }

    /** Actualiza una editorial
     *
     * @param editorial
     * @param id
     * @return un estado http
     */
    @PutMapping("/editorials/{id}")
    public ResponseEntity<?> update(@RequestBody Editorial editorial,@PathVariable Integer id){
        try{
            // Si se encuentra una editorial con el id dado, se actualiza y el servidor devuelve el estado HTTP OK.
            Editorial existEditorial= edService.get(id);
            existEditorial.setName(editorial.getName());
            System.out.println(editorial.getName());
            existEditorial.setWeb_site(editorial.getWeb_site());
            edService.save(existEditorial);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoSuchElementException e){
            //si no se encuentra ninguna editorial, se devuelve el estado HTTP Not found (404).
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Actualiza una editorial con el método patch
     *
     * @param id
     * @param fields
     * @return la editorial modificada
     */
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

    /** Elimina una editorial dado su id
     *
     * @param id
     */
    @DeleteMapping("/editorials/{id}")
    public void delete(@PathVariable Integer id){
        edService.delete(id);
    }
}