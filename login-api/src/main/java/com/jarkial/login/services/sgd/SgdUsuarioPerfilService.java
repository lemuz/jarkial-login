package com.jarkial.login.services.sgd;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.model.entity.sgd.SgdUsuarioPerfil;
import com.jarkial.login.services.AbstractCrudService;

import java.util.List;

public interface SgdUsuarioPerfilService extends AbstractCrudService<SgdUsuarioPerfil, Long>{

    List<SgdUsuarioPerfil> findAllBySgdUsuario(SgdUsuario sgdUsuario)throws Exception;
    
/*
    List<SgdUsuarioPerfil> findAll() throws Exception;

    SgdUsuarioPerfil findById(Long id) throws Exception;

    SgdUsuarioPerfil update(SgdUsuarioPerfil sgdUsuarioPerfil) throws Exception;

    boolean deleteById(Long id) throws Exception;
 */    
}