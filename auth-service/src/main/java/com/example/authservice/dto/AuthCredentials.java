package com.example.authservice.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthCredentials implements Serializable{

    private String username;
    private String pass;

    //agregar parametro con la lisra de GrantedAutority

    /**
     * @return String return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

}