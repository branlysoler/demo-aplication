package com.example.demo.security_config;

import lombok.Data;

@Data
public class AuthCredentials {

    private String email;
    private String pass;

    //agregar parametro con la lisra de GrantedAutority
    

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

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

}