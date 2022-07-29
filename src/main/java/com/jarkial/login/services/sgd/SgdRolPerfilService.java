package com.jarkial.login.services.sgd;

import java.util.List;

import com.jarkial.login.services.AbstractCrudService;
import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.model.entity.sgd.SgdRolPerfil;


public interface SgdRolPerfilService extends AbstractCrudService<SgdRolPerfil, Long>{

    List<SgdRolPerfil> findAllBySgdPerfil(CtgCatalogo sgdPerfil)throws Exception;

/* 
    List<SgdRolPerfil> findAll() throws Exception;

    SgdRolPerfil findById(Long id) throws Exception;

    SgdRolPerfil update(SgdRolPerfil sgdRolPerfil) throws Exception;

    boolean deleteById(Long id) throws Exception;
*/
}