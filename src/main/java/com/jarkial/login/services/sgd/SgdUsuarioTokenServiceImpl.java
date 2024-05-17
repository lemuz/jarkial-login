package com.jarkial.login.services.sgd;

import com.jarkial.login.model.entity.sgd.SgdUsuarioToken;
import com.jarkial.login.repositories.sgd.SgdUsuarioTokenRepository;
import com.jarkial.login.services.AbstractBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SgdUsuarioTokenServiceImpl extends AbstractBaseServiceImpl implements SgdUsuarioTokenService{

    @Autowired
    SgdUsuarioTokenRepository sgdUsuarioTokenRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<SgdUsuarioToken> findAll() throws Exception {
        return sgdUsuarioTokenRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SgdUsuarioToken findById(String id) throws Exception {
        return sgdUsuarioTokenRepository.findById(id).orElse(null);

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public SgdUsuarioToken update(SgdUsuarioToken sgdUsuarioToken) throws Exception {
        return sgdUsuarioTokenRepository.save(sgdUsuarioToken);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public boolean deleteById(String id) throws Exception {
        try{
            sgdUsuarioTokenRepository.deleteById(id);
        }catch(Exception exception){
            exception.printStackTrace();
            return false;
        }
        return true;
    }   
}