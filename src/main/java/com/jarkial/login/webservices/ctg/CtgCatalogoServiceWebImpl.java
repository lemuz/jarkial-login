package com.jarkial.login.webservices.ctg;

import com.jarkial.login.services.ctg.CtgCatalogoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CtgCatalogoServiceWebImpl implements CtgCatalogoServiceWeb{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CtgCatalogoService ctgCatalogoService;

}