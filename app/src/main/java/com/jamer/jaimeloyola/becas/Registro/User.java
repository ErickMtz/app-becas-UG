package com.jamer.jaimeloyola.becas.Registro;

/**
 * Created by Jaime Loyola on 23/8/2016.
 *
 * Esta clase es un reflejo en Java de los atributos de los objetos JSON
 * que vienen desde el servidor.
 */
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
