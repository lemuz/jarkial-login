package com.jarkial.login.repositories.sgd;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.model.entity.sgd.SgdRolPerfil;

@Repository
public interface SgdRolPerfilRepository extends JpaRepository<SgdRolPerfil, Long>{
    
    List<SgdRolPerfil> findAllBySgdPerfil(CtgCatalogo sgdPerfil)throws Exception;
}