package com.jarkial.login.repositories.gst;

import com.jarkial.login.model.entity.gst.GstLog;
import com.jarkial.login.repositories.AbstractBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GstLogRepository extends AbstractBaseRepository<GstLog, Long>{
    
}