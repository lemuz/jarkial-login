package com.jarkial.login.repositories.sgd;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.repositories.AbstractBaseRepository;

@Repository
public interface SgdUsuarioRepository extends AbstractBaseRepository<SgdUsuario, Long>{
    
    Optional<SgdUsuario> findBySgdUsuarioUsername(String username);
}