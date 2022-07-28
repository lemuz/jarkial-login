package com.jarkial.login.repositories.ctg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.ctg.CtgAgencia;

@Repository
public interface CtgAgenciaRepository extends JpaRepository<CtgAgencia, Long>{
    
}
