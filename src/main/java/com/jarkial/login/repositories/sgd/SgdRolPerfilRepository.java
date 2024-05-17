package com.jarkial.login.repositories.sgd;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.model.entity.sgd.SgdRolPerfil;
import com.jarkial.login.repositories.AbstractBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SgdRolPerfilRepository extends AbstractBaseRepository<SgdRolPerfil, Long>{
    
    List<SgdRolPerfil> findAllBySgdPerfil(CtgCatalogo sgdPerfil)throws Exception;
}