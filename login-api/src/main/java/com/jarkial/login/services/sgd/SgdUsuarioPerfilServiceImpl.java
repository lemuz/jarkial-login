package com.jarkial.login.services.sgd;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.model.entity.sgd.SgdUsuarioPerfil;
import com.jarkial.login.repositories.sgd.SgdUsuarioPerfilRepository;
import com.jarkial.login.services.AbstractBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SgdUsuarioPerfilServiceImpl extends AbstractBaseServiceImpl implements SgdUsuarioPerfilService{

    @Autowired
    SgdUsuarioPerfilRepository sgdUsuarioPerfilRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<SgdUsuarioPerfil> findAll() throws Exception {
        return sgdUsuarioPerfilRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SgdUsuarioPerfil findById(Long id) throws Exception {
        return sgdUsuarioPerfilRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public SgdUsuarioPerfil update(SgdUsuarioPerfil entity) throws Exception {
        return sgdUsuarioPerfilRepository.save(entity);

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public boolean deleteById(Long id) throws Exception {
        try{
            sgdUsuarioPerfilRepository.deleteById(id);
        }catch(Exception exception){
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<SgdUsuarioPerfil> findAllBySgdUsuario(SgdUsuario sgdUsuario) throws Exception {
        return sgdUsuarioPerfilRepository.findAll();
    }
}