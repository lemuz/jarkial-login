package com.jarkial.login.webservices.sgd;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.services.sgd.SgdUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SgdUsuarioServiceWebImpl implements SgdUsuarioServiceWeb{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SgdUsuarioService sgdUsuarioService;

    @Override
    public String actualizarSgdUsuarioLogueado(String sgdUsuarioUsername, Integer logueado) {
        logger.info("Actualizar usuario logueado: " + sgdUsuarioUsername + " logueado: " + logueado);
        String ms = "OK";
        try{
            if(sgdUsuarioUsername != null && !sgdUsuarioUsername.isEmpty() && logueado != null){
                SgdUsuario sgdUsuario = sgdUsuarioService.findBySgdUsuarioUsername(sgdUsuarioUsername);
                if(sgdUsuario != null){
                    //chequear si se limpiara el contexto  cuando la accion sea deslogeo
                    sgdUsuario.setSgdUsuarioLogueado(logueado);
                    sgdUsuarioService.update(sgdUsuario);
                    logger.info("Usuario actualizado exitosamente!");
                }else{
                    ms = "Usuario no encontrado";
                    logger.warn(ms);
                }
            }else{
                ms = "Parametros incompletos";
                logger.warn(ms);
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return ms;
        
    }  
   
}