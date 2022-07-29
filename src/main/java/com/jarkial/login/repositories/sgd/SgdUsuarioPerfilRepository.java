package com.jarkial.login.repositories.sgd;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.model.entity.sgd.SgdUsuarioPerfil;

@Repository
public interface SgdUsuarioPerfilRepository extends JpaRepository<SgdUsuarioPerfil, Long>{
    
    List<SgdUsuarioPerfil> findAllBySgdUsuario(SgdUsuario sgdUsuario)throws Exception;
}