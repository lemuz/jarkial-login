package com.jarkial.login.model.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class OutResponse implements Serializable{

    public static final String COD_JWT_MISSING = "08";
    public static final String MSG_JWT_MISSING = "No se proporciono el token de autorizacion";

    public static final String COD_JWT_EXPIRED = "09";
    public static final String MSG_JWT_EXPIRED = "El token proporcionado expiro";

    public static final String COD_JWT_INVALID = "10";
    public static final String MSG_JWT_INVALID = "El token proporcionado es invalido";

    public static final String COD_JWT_SESSION_DUPLICATE = "11";
    public static final String MSG_JWT_SESSION_DUPLICATE = "Usuario ha iniciado sesion en otro dispositivo";

    @Getter @Setter
    public Object content;

    @Getter @Setter
    public Date timestamp = new Date();

    @Getter @Setter
    public String url;

    @Getter @Setter
    public String errors;

    @Getter @Setter
    public String message;

    @Getter @Setter
    public String code;
    
}
