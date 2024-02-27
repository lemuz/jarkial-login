package com.jarkial.login.services.sgd;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.model.entity.sgd.SgdRolPerfil;
import com.jarkial.login.repositories.sgd.SgdRolPerfilRepository;
import com.jarkial.login.services.AbstractBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SgdRolPerfilServiceImpl extends AbstractBaseServiceImpl implements SgdRolPerfilService{

    @Autowired
    SgdRolPerfilRepository sgdRolPerfilRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<SgdRolPerfil> findAll() throws Exception {
        return sgdRolPerfilRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SgdRolPerfil findById(Long id) throws Exception {
        return sgdRolPerfilRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public SgdRolPerfil update(SgdRolPerfil entity) throws Exception {
        return sgdRolPerfilRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public boolean deleteById(Long id) throws Exception {
        try{
            sgdRolPerfilRepository.deleteById(id);
        }catch(Exception exception){
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<SgdRolPerfil> findAllBySgdPerfil(CtgCatalogo sgdPerfil) throws Exception {
        return sgdRolPerfilRepository.findAllBySgdPerfil(sgdPerfil);

    }
    
}