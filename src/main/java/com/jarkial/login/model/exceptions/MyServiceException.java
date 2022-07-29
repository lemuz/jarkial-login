package com.jarkial.login.model.exceptions;

import lombok.Getter;
import lombok.Setter;

public class MyServiceException  extends Exception{

    @Getter @Setter
    private String code = "";

    /**
     * @param message
     */
    public MyServiceException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public MyServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public MyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param code
     * @param message
     */
    public MyServiceException(String code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * @param message
     * @param cause
     * @param code
     */
    public MyServiceException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }
}
