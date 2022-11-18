package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;
//Clase donde se implementan los métodos declarados en el servicio
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** Obtiene una lista de todos los usuarios activos en la BD
     *
     * @return la lista de usuarios
     */
    @GetMapping("/users")
    public List<User> list(){
        List<User> activos = new ArrayList<>();
        for(User actual: userService.listAll()){
            if(actual.isActivo()){
                activos.add(actual);
            }
        }
        return activos;
    }

    /** Obtiene información sobre un usuario dado un username
     *
     * @param username
     * @return el usuario y un estado http
     */
    @GetMapping("/users/{username}")
    public ResponseEntity<User> get(@PathVariable("username") final String username){
        try{
            //si se encuentra una editorial para el ID dado, el servidor envía una respuesta que incluye
            // la representación JSON del objeto con el estado HTTP OK (200)
            User user= userService.get(username);
            if(user.isActivo()){
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch(NoSuchElementException e){
            //si no se encuentra ningún producto, devuelve el estado HTTP status Not Found (404).
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    /** Crea un nuevo usuario
     *
     * @param user
     */
    @PostMapping("/users")
    public void add(@RequestBody User user){
        userService.save(user);
    }

    /** Actualiza un usuario dado un username
     *
     * @param user
     * @param username
     * @return un estado http
     */
    @PutMapping("/users/{username}")
    public ResponseEntity<?> update(@RequestBody User user,@PathVariable("username") final String username){
        try{
            // Si se encuentra una editorial con el id dado, se actualiza y el servidor devuelve el estado HTTP OK.
            User existUser = userService.get(username);
            existUser.setNombre(user.getNombre());
            existUser.setApellido(user.getApellido());
            existUser.setFechaNacimiento(user.getFechaNacimiento());
            existUser.setCargo(user.getCargo());
            userService.save(existUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoSuchElementException e){
            //si no se encuentra ninguna editorial, se devuelve el estado HTTP Not found (404).
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Actualiza un usuario con el método patch
     *
     * @param username
     * @param fields
     * @return el usuario modificado
     */
    @PatchMapping("/users/{username}")
    public ResponseEntity<?> patch(@PathVariable("username") final String username, @RequestBody Map<String,Object> fields){
        User existingUser = userService.get(username);
        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(User.class,key);
            if(field !=null){
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingUser,value);
            }
        });
        User result = userService.save(existingUser);
        return ResponseEntity.ok(result);
    }

    /** Inactiva un usuario dado un username
     *
     * @param username
     * @return el usuario inactivo
     */
    @DeleteMapping("/users/{username}")
    public ResponseEntity<User> delete(@PathVariable("username") final String username){
        User existingUser = userService.get(username);
        existingUser.setActivo(false);
        User result = userService.save(existingUser);
        return ResponseEntity.ok(result);
    }

    /** Filtrar usuarios dado un nombre
     *
     * @param nombre
     * @return lista de usuarios
     */
    @Modifying
    @GetMapping("/user/{nombre}")
    public ResponseEntity<List<User>> filterByName(@PathVariable("nombre") String nombre){
        List<User> users= userService.filterByName("%" + nombre + "%");
        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
}