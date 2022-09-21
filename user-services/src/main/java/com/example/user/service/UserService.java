package com.example.user.service;

import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder bCript;

    public List<User> listAll(){
        return repo.findAll();
    }
    public User save(User user){
        String contraseniaVista = user.getContrasenia();
        user.setContrasenia(bCript.encode(contraseniaVista));
        return repo.save(user);
    }
    public User get(Integer id){
        return repo.findById(id).get();
    }
    public void delete(Integer id){
        repo.deleteById(id);
    }

    public List<User> filterByName(String name){
        return repo.findUserByName(name);
    }
}
