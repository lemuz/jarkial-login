package com.jarkial.login.repositories.ctg;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.repositories.AbstractBaseRepository;

@Repository
public interface CtgCatalogoRepository extends AbstractBaseRepository<CtgCatalogo, Long>{

    Optional<CtgCatalogo> findByCtgCatalogoNombreAndCtgCatalogoPadre_CtgCatalogoId(String ctgCatalogoNombre, Long ctgCatalogoPadreId);
    
}
