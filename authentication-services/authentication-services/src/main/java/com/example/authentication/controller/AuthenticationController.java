package com.example.authentication.controller;

import com.example.authentication.dto.UserAuth;
import com.example.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//Clase donde se implementan los métodos declarados en el servicio
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;

    /** Hace el login dado un usuario y contraseña
     *
     * @param username
     * @param pwd
     * @return un userAuth
     */
    @PostMapping("login")
    public UserAuth login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        return authService.login(username, pwd);
    }
}
