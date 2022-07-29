package com.jarkial.login.configuration.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jarkial.login.configuration.security.SgdUsuarioDetailsServiceImpl;
import com.jarkial.login.configuration.utils.JwtTokenUtils;
import com.jarkial.login.model.entity.sgd.SgdUsuarioToken;
import com.jarkial.login.repositories.sgd.SgdUsuarioTokenRepository;

@Component
public class JwtAuthFilter  extends OncePerRequestFilter{

    @Autowired
    JwtTokenUtils jwtTokenUtil;

    @Autowired
    SgdUsuarioTokenRepository sgdUsuarioTokenRepository;

    @Autowired
    SgdUsuarioDetailsServiceImpl sgdUsuarioDetailsServiceImpl;

    private static final String variableResponse = "responseDTO";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //
        filterChain.doFilter(request, response);
    }   
}