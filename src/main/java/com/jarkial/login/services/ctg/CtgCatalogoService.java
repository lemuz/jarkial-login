package com.jarkial.login.services.ctg;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.services.AbstractCrudService;

public interface CtgCatalogoService extends AbstractCrudService<CtgCatalogo, Long>{

    CtgCatalogo findByCtgCatalogoNombreAndCtgCatalogoPadre(String string, String string2);
}