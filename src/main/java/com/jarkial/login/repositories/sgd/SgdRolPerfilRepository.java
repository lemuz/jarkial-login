package com.jarkial.login.repositories.sgd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.sgd.SgdRolPerfil;

@Repository
public interface SgdRolPerfilRepository extends JpaRepository<SgdRolPerfil, Long>{
    
}
