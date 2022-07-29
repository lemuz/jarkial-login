package com.jarkial.login.services.gst;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jarkial.login.model.entity.gst.GstLog;
import com.jarkial.login.services.AbstractBaseServiceImpl;

@Service
@Transactional
public class GstLogServiceImpl extends AbstractBaseServiceImpl implements GstLogService{

    @Override
    public List<GstLog> findAll() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GstLog findById(Long id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GstLog update(GstLog entity) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public GstLog crearGstLog(String string, String ipAddress) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
