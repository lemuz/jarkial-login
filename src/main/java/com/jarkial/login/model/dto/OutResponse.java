package com.jarkial.login.model.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class OutResponse implements Serializable{

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
    public String codigo;
    
}
