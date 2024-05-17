package com.jarkial.login.repositories.sgd;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.model.entity.sgd.SgdUsuarioPerfil;
import com.jarkial.login.repositories.AbstractBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SgdUsuarioPerfilRepository extends AbstractBaseRepository<SgdUsuarioPerfil, Long>{
    
    List<SgdUsuarioPerfil> findAllBySgdUsuario(SgdUsuario sgdUsuario)throws Exception;
}