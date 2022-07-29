package com.jarkial.login.webservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jarkial.login.services.sgd.SgdUsuarioService;

public class SgdUsuarioServiceWebImpl implements SgdUsuarioServiceWeb{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SgdUsuarioService sgdUsuarioService;

    @Override
    public void actualizarSgdUsuarioLogueado(String sgdUsuarioUsername, Integer logueado) {
        logger.info("Actualizar usuario logueado: " + sgdUsuarioUsername + " logueado: " + logueado);
        String ms = "OK";
        try{
            if(sgdUsuarioUsername != null && !sgdUsuarioUsername.isEmpty() && logueado != null){

            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        
    }  
   
}