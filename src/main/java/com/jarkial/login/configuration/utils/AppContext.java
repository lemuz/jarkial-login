package com.jarkial.login.configuration.utils;

import com.jarkial.login.configuration.security.SgdUsuarioDetailsServiceImpl;

public class AppContext {

    public static SgdUsuarioDetailsServiceImpl getSgdUsuarioDetailsServiceImpl(){
        return ContextProvider.getApplicationContext().getBean(SgdUsuarioDetailsServiceImpl.class);
    }
    
}
