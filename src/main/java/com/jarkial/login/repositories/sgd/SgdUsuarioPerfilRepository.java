package com.jarkial.login.repositories.sgd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.sgd.SgdUsuarioPerfil;

@Repository
public interface SgdUsuarioPerfilRepository extends JpaRepository<SgdUsuarioPerfil, Long>{
    
}
