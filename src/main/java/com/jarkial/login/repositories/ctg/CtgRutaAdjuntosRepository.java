package com.jarkial.login.repositories.ctg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.ctg.CtgRutaAdjuntos;

@Repository
public interface CtgRutaAdjuntosRepository extends JpaRepository<CtgRutaAdjuntos, Long>{
    
}
