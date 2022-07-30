package com.jarkial.login.services.ctg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.repositories.ctg.CtgCatalogoRepository;
import com.jarkial.login.services.AbstractBaseServiceImpl;

@Service
@Transactional
public class CtgCatalogoServiceImpl extends AbstractBaseServiceImpl implements CtgCatalogoService{

    @Autowired
    CtgCatalogoRepository ctgCatalogoRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CtgCatalogo> findAll() throws Exception {
        return ctgCatalogoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CtgCatalogo findById(Long id) throws Exception {
        return ctgCatalogoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public CtgCatalogo update(CtgCatalogo entity) throws Exception {
        return ctgCatalogoRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public boolean deleteById(Long id) throws Exception {
        try{
            ctgCatalogoRepository.deleteById(id);
        }catch(Exception exception){
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CtgCatalogo findByCtgCatalogoNombreAndCtgCatalogoPadre(String nombre, Long ctgCatalogoPadreId) throws Exception{
        return ctgCatalogoRepository.findByCtgCatalogoNombreAndCtgCatalogoPadre_CtgCatalogoId(nombre, ctgCatalogoPadreId).orElse(null);
    }
    
}