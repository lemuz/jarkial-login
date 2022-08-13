package com.jarkial.login.repositories.sgd;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.model.entity.sgd.SgdRolPerfil;
import com.jarkial.login.repositories.AbstractBaseRepository;

@Repository
public interface SgdRolPerfilRepository extends AbstractBaseRepository<SgdRolPerfil, Long>{
    
    List<SgdRolPerfil> findAllBySgdPerfil(CtgCatalogo sgdPerfil)throws Exception;
}