package com.jarkial.login.controllers.security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jarkial.login.configuration.security.SgdUsuarioDetailsServiceImpl;
import com.jarkial.login.controllers.AbstractBaseController;
import com.jarkial.login.model.dto.sgd.CustomUser;
import com.jarkial.login.model.dto.sgd.Login;
import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.model.exceptions.MyServiceException;
import com.jarkial.login.services.ctg.CtgCatalogoService;
import com.jarkial.login.services.sgd.SgdUsuarioService;
import com.jarkial.login.webservices.ctg.CtgCatalogoServiceWeb;

@RestController
@RequestMapping("/security")
public class LoginController extends AbstractBaseController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SgdUsuarioService sgdUsuarioService;

    @Autowired
    SgdUsuarioDetailsServiceImpl sgdUsuarioDetailsServiceImpl;

    @Autowired
    CtgCatalogoService ctgCatalogoService;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity login(@RequestBody @NotBlank @Valid Login loginDto, HttpServletRequest request, HttpServletResponse response) throws MyServiceException{
        ResponseEntity<?> responseEntity = new ResponseEntity<>("Usuario se encuentra inactivo", HttpStatus.ACCEPTED);
        loginDto.setIpAddress(getClientIpAddress(request));
        logger.info("login");
        CtgCatalogo mensaje;
        try{
            mensaje = ctgCatalogoService.findByCtgCatalogoNombreAndCtgCatalogoPadreId("LOGIN", 92927L);
        }catch(Exception exception){
            exception.printStackTrace();
            return generateErrorResponseWithCode(exception, "Error al obtener mensaje", HttpStatus.OK, "10000");
        }
        try{
            Map resp = new LinkedHashMap();
            resp = sgdUsuarioDetailsServiceImpl.login(loginDto);
            responseEntity = new ResponseEntity<>(resp, HttpStatus.OK);
        }catch(BadCredentialsException | NullPointerException exception){
            exception.printStackTrace();
            responseEntity = generateErrorResponseWithCode(exception, mensaje.getCtgCatalogoDescripcion(), HttpStatus.ACCEPTED, "00101");
        }catch(Exception exception){
            exception.printStackTrace();
            responseEntity = generateErrorResponseWithCode(exception, exception.getMessage(), HttpStatus.OK, "10000");
        }
        return responseEntity;
    }
 
    @RequestMapping("/accessDenied")
    public ResponseEntity<?> accessDenied(){
        ResponseEntity<?> response = null;
        response = generateSingleResponseWithCode("Accesso Denegado", "", HttpStatus.FORBIDDEN, "403");
        return response;
    }

    @RequestMapping("/unauthorized")
    public ResponseEntity<?> unauthorized(){
        ResponseEntity<?> response = null;
        response = generateSingleResponseWithCode("Sin autenticación", "", HttpStatus.UNAUTHORIZED, "401");
        return response;
    }
}