package com.example.user.entity;

public class LoginData {

    private String username;

    private String contrasenia;

    public LoginData(String username, String contrasenia) {
        this.username = username;
        this.contrasenia = contrasenia;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
