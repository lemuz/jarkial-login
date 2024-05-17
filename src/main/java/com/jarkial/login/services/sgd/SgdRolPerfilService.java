package com.jarkial.login.services.sgd;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.model.entity.sgd.SgdRolPerfil;
import com.jarkial.login.services.AbstractCrudService;

import java.util.List;


public interface SgdRolPerfilService extends AbstractCrudService<SgdRolPerfil, Long>{

    List<SgdRolPerfil> findAllBySgdPerfil(CtgCatalogo sgdPerfil)throws Exception;

/* 
    List<SgdRolPerfil> findAll() throws Exception;

    SgdRolPerfil findById(Long id) throws Exception;

    SgdRolPerfil update(SgdRolPerfil sgdRolPerfil) throws Exception;

    boolean deleteById(Long id) throws Exception;
*/
}