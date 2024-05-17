package com.jarkial.login.repositories.sgd;

import com.jarkial.login.model.entity.sgd.SgdUsuarioToken;
import com.jarkial.login.repositories.AbstractBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SgdUsuarioTokenRepository extends AbstractBaseRepository<SgdUsuarioToken, String>{
    
}
