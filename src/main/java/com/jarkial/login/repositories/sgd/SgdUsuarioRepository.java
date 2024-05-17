package com.jarkial.login.repositories.sgd;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.repositories.AbstractBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SgdUsuarioRepository extends AbstractBaseRepository<SgdUsuario, Long>{
    
    Optional<SgdUsuario> findBySgdUsuarioUsername(String username);
}