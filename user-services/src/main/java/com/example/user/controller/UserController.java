package com.example.user.controller;

import com.example.user.JWTUtil;
import com.example.user.UserDetailService;
import com.example.user.entity.JWTResponse;
import com.example.user.entity.LoginData;
import com.example.user.entity.User;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JWTUtil utils;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //RESTful API methods for retrieval operations
    @GetMapping("/users")//Return a list of all the users in the BD
    public List<User> list(){
        List<User> activos = new ArrayList<>();
        for(User actual: userService.listAll()){
            if(actual.isActivo()){
                activos.add(actual);
            }
        }
        return activos;
    }

    @GetMapping("/users/{id}")//Get information about a specific product based on ID
    public ResponseEntity<User> get(@PathVariable Integer id){
        try{
            //if a product is found for the given ID, the server sends a response that includes JSON representation of
            // the Product object with HTTP status OK (200)
            User user= userService.get(id);
            if(user.isActivo()){
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch(NoSuchElementException e){
            //if no product is found, it returns HTTP status Not Found (404).
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    public void autenticar(String username, String password) throws Exception {
        try{
            auth.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch(Exception e){
            throw new Exception("Credenciales erroneas");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginData user) throws Exception {
        try{
            autenticar(user.getUsername(), user.getContrasenia());
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
            String token = utils.getJWTToken(userDetails);
            return new ResponseEntity<JWTResponse>(new JWTResponse(token), HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<JWTResponse>(new JWTResponse("Credenciales erroneas"), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/users")
    public void add(@RequestBody User user){
        userService.save(user);
    }

    //Update a User
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody User user,@PathVariable Integer id){
        try{
            // If a product found with the given ID, it is updated and the server returns HTTP status OK.
            User existUser = userService.get(id);
            existUser.setNombre(user.getNombre());
            existUser.setApellido(user.getApellido());
            existUser.setFechaNacimiento(user.getFechaNacimiento());
            existUser.setCargo(user.getCargo());
            userService.save(existUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoSuchElementException e){
            //if no product found, the HTTP status Not Found (404) is returned.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> patch(@PathVariable Integer id, @RequestBody Map<String,Object> fields){
        User existingUser = userService.get(id);
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

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id){
        User existingUser = userService.get(id);
        existingUser.setActivo(false);
        User result = userService.save(existingUser);
        return ResponseEntity.ok(result);
    }

    @Modifying
    @GetMapping("/user/{nombre}") //Filter Users by a name.
    public ResponseEntity<List<User>> filterByName(@PathVariable("nombre") String nombre){
        List<User> users= userService.filterByName("%" + nombre + "%");
        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
}
