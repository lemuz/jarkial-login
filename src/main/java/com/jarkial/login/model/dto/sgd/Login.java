package com.jarkial.login.model.dto.sgd;

import lombok.Getter;
import lombok.Setter;

public class Login {

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String ipAddress;

    /**
     * @param username
     * @param password
     */
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 
     */
    public Login() {
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    
    @Override
    public String toString() {
        return "Login { \"password\": \"" + password + "\", \"username\": \"" + username + "\" }";
    }

    
    
}
