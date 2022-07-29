package com.jarkial.login.repositories.sgd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.sgd.SgdUsuarioToken;

@Repository
public interface SgdUsuarioTokenRepository extends JpaRepository<SgdUsuarioToken, Long>{
    
}
