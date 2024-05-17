package com.jarkial.login.controllers.security;

import com.jarkial.login.configuration.security.SgdUsuarioDetailsServiceImpl;
import com.jarkial.login.configuration.utils.MyUtils;
import com.jarkial.login.controllers.AbstractBaseController;
import com.jarkial.login.model.dto.sgd.Login;
import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.model.exceptions.MyServiceException;
import com.jarkial.login.services.ctg.CtgCatalogoService;
import com.jarkial.login.services.sgd.SgdUsuarioService;
import com.jarkial.login.webservices.sgd.SgdUsuarioServiceWeb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @Autowired
    SgdUsuarioServiceWeb sgdUsuarioServiceWeb;


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

    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        ResponseEntity<?> response = null;
        String resultado = null;
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        logger.info("LoginController logout usuario: " + auth.getName());
        try{
            resultado = sgdUsuarioServiceWeb.actualizarSgdUsuarioLogueado(auth.getName(), 0);
            if(resultado.equals("OK")){
                SecurityContextHolder.clearContext();
                response = generateSingleResponseWithCode(SUCCESS, resultado, HttpStatus.OK, "00000");
            }else {
                response = generateSingleResponseWithCode(resultado, null, HttpStatus.ACCEPTED, "00400");
            }
        }catch(PropertyReferenceException e){
            e.printStackTrace();
            return generateErrorResponseWithCode(e, this.getMessage("Propiedad no exisstente en modelo"), HttpStatus.BAD_REQUEST, "00002");
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
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