package com.jarkial.login.services.sgd;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.services.AbstractCrudService;

public interface SgdUsuarioService extends AbstractCrudService<SgdUsuario, Long>{

    SgdUsuario findBySgdUsuarioUsername(String username) throws Exception;

/*
    List<SgdUsuario> findAll() throws Exception;

    SgdUsuario findById(Long id) throws Exception;

    SgdUsuario update(SgdUsuario sgdUsuario) throws Exception;

    boolean deleteById(Long id) throws Exception;
 */    
}