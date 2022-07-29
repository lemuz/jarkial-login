package com.jarkial.login.configuration.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextProvider implements ApplicationContextAware{

    @Autowired
    static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){ return applicationContext; }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextProvider.applicationContext = applicationContext;
        
    }
    
}
