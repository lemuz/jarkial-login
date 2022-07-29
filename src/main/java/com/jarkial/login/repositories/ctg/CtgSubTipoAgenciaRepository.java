package com.jarkial.login.repositories.ctg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.ctg.CtgSubTipoAgencia;

@Repository
public interface CtgSubTipoAgenciaRepository extends JpaRepository<CtgSubTipoAgencia, Long>{
    
}
