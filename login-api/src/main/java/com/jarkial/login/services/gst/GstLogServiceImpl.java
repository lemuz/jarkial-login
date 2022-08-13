package com.jarkial.login.services.gst;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jarkial.login.configuration.utils.MyUtils;
import com.jarkial.login.model.entity.gst.GstLog;
import com.jarkial.login.repositories.gst.GstLogRepository;
import com.jarkial.login.services.AbstractBaseServiceImpl;

@Service
@Transactional
public class GstLogServiceImpl extends AbstractBaseServiceImpl implements GstLogService{

    @Autowired
    GstLogRepository gstLogRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<GstLog> findAll() throws Exception {
        return gstLogRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public GstLog findById(Long id) throws Exception {
        return gstLogRepository.findById(id).orElse(null);

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public GstLog update(GstLog entity) throws Exception {
        return gstLogRepository.save(entity);

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public boolean deleteById(Long id) throws Exception {
        try{
            gstLogRepository.deleteById(id);
        }catch(Exception exception){
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class}, propagation = Propagation.SUPPORTS)
    public GstLog crearGstLog(String operacion, String ipAddress) throws Exception{
        GstLog gstLog = new GstLog();
        gstLog.setGstConfLogIp(ipAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        gstLog.setGstConfLogUsuario(auth.getName());
        gstLog.setGstConfLogTipoTransacion(operacion);
        gstLog.setGstConfLogClase(MyUtils.getClassName());
        GstLog gstLogR = update(gstLog);
        return gstLogR;
    }
    
}
