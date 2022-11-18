package com.example.authentication.service;

import com.example.authentication.dto.UserAuth;
import com.example.authentication.repository.AuthenticationRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationRepository repo;


    @Autowired
    private PasswordEncoder encoder; //Encriptador de contraseñas

    /** Hace el login dado un usuario y contraseña
     *
     * @param username
     * @param pwd
     * @return un userAuth
     */
    public UserAuth login(String username, String pwd) {

        String token = getJWTToken(username);
        UserAuth user = new UserAuth();
        user.setUsername(username);
        user.setPassword(pwd);
        user.setToken(token);
        //Se usa el metodo matches para comparar la contraseña ingresada al momento de logearse
        // y se compara con la encriptada en la BD.
        boolean isPasswordMatch = encoder.matches(pwd,repo.findById(username).get().getContrasenia());

        if(repo.findById(username).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe ese usuario");
        if(isPasswordMatch) {
            return user;
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña mal escrita");
        }
    }

    /** Toma el JSON Web Token (JWT) para las credenciales de usuario proporcionadas
     *
     * @param username
     * @return el token
     */
    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}