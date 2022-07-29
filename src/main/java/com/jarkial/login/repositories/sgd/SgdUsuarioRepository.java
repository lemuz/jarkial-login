package com.jarkial.login.repositories.sgd;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.sgd.SgdUsuario;

@Repository
public interface SgdUsuarioRepository extends JpaRepository<SgdUsuario, Long>{
    
    Optional<SgdUsuario> findBySgdUsuarioUsername(String username);
}