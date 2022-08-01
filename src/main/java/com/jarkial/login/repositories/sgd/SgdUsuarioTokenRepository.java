package com.jarkial.login.repositories.sgd;

import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.sgd.SgdUsuarioToken;
import com.jarkial.login.repositories.AbstractBaseRepository;

@Repository
public interface SgdUsuarioTokenRepository extends AbstractBaseRepository<SgdUsuarioToken, String>{
    
}
