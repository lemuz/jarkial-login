package com.jarkial.login.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        logger.warn("######################## EntryPoint!");
        try{
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.setAttribute("exceptionMessage", authException.getMessage());
            request.setAttribute("exception", authException);
            response.getOutputStream().println("{\"error\": \"" + authException.getMessage() + "\" }");
        }catch(IOException exception){
            exception.printStackTrace();
        }
        
    }   
}