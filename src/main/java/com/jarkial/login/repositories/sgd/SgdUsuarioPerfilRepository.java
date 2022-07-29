package com.jarkial.login.repositories.sgd;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.model.entity.sgd.SgdUsuarioPerfil;
import com.jarkial.login.repositories.AbstractBaseRepository;

@Repository
public interface SgdUsuarioPerfilRepository extends AbstractBaseRepository<SgdUsuarioPerfil, Long>{
    
    List<SgdUsuarioPerfil> findAllBySgdUsuario(SgdUsuario sgdUsuario)throws Exception;
}