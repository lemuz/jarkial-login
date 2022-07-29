package com.jarkial.login.services.gst;

import com.jarkial.login.model.entity.gst.GstLog;
import com.jarkial.login.services.AbstractCrudService;

public interface GstLogService extends AbstractCrudService<GstLog, Long>{

    GstLog crearGstLog(String string, String ipAddress) throws Exception;
    
}
