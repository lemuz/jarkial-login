package com.jarkial.login.repositories.gst;

import org.springframework.stereotype.Repository;

import com.jarkial.login.model.entity.gst.GstLog;
import com.jarkial.login.repositories.AbstractBaseRepository;

@Repository
public interface GstLogRepository extends AbstractBaseRepository<GstLog, Long>{
    
}