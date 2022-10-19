package com.example.user.service;

import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder bCript; //Para encriptar la contraseña en el BD.

    public List<User> listAll(){
        return repo.findAll();
    }
    public User save(User user){
        String contraseniaVista = user.getContrasenia();
        user.setContrasenia(bCript.encode(contraseniaVista));//se encripta la contraseña
        return repo.save(user);
    }
    public User get(String username){
        return repo.findById(username).get();
    }
    public List<User> filterByName(String name){
        return repo.findUserByName(name);
    }
}
