package com.jarkial.login.services.sgd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jarkial.login.model.sgd.SgdUsuarioToken;
import com.jarkial.login.repositories.sgd.SgdUsuarioTokenRepository;
import com.jarkial.login.services.AbstractBaseServiceImpl;

@Service
@Transactional
public class SgdUsuarioTokenServiceImpl extends AbstractBaseServiceImpl<SgdUsuarioToken, Long>{

    @Autowired
    SgdUsuarioTokenRepository sgdUsuarioTokenRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<SgdUsuarioToken> findAll() throws Exception {
        return sgdUsuarioTokenRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SgdUsuarioToken findById(Long id) throws Exception {
        return sgdUsuarioTokenRepository.findById(id).orElse(null);

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public SgdUsuarioToken update(SgdUsuarioToken sgdUsuarioToken) throws Exception {
        return sgdUsuarioTokenRepository.save(sgdUsuarioToken);
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }
    
}