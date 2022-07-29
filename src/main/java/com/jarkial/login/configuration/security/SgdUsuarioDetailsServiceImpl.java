package com.jarkial.login.configuration.security;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.common.util.impl.Log_.logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jarkial.login.configuration.utils.JwtTokenUtils;
import com.jarkial.login.configuration.utils.MyUtils;
import com.jarkial.login.model.dto.sgd.CustomUser;
import com.jarkial.login.model.dto.sgd.Login;
import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.model.entity.gst.GstLog;
import com.jarkial.login.model.entity.sgd.SgdRolPerfil;
import com.jarkial.login.model.entity.sgd.SgdUsuario;
import com.jarkial.login.model.entity.sgd.SgdUsuarioPerfil;
import com.jarkial.login.model.entity.sgd.SgdUsuarioToken;
import com.jarkial.login.model.exceptions.MyServiceException;
import com.jarkial.login.services.ctg.CtgCatalogoService;
import com.jarkial.login.services.gst.GstLogService;
import com.jarkial.login.services.sgd.SgdRolPerfilService;
import com.jarkial.login.services.sgd.SgdUsuarioPerfilService;
import com.jarkial.login.services.sgd.SgdUsuarioService;
import com.jarkial.login.services.sgd.SgdUsuarioTokenService;

@Service
public class SgdUsuarioDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SgdUsuarioService sgdUsuarioService;

    @Autowired
    SgdUsuarioTokenService sgdUsuarioTokenService;

    @Autowired
    CtgCatalogoService ctgCatalogoService;

    @Autowired
    SgdUsuarioPerfilService sgdUsuarioPerfilService;

    @Autowired
    SgdRolPerfilService sgdRolPerfilService;

    @Autowired
    GstLogService gstLogService;
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final SgdUsuario usuario = sgdUsuarioService.findBySgdUsuarioUsername(username);
            if (usuario == null)
                throw new UsernameNotFoundException("Username invalido");
            boolean estado = (usuario.getSgdUsuarioActivo().equals("1"));
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (estado)
                authorities = getAuthorities(usuario);
            CustomUser detailsUser = new CustomUser(usuario.getSgdUsuarioUsername(), usuario.getSgdUsuarioClave(),
                    estado, estado, estado, estado, authorities);
            return detailsUser;
        } catch (Exception exception) {
            logger.info(exception.getMessage());
            return null;
        }
    }

    private List<GrantedAuthority> getAuthorities(SgdUsuario usuario) throws Exception {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<SgdUsuarioPerfil> perfiles = sgdUsuarioPerfilService.findAllBySgdUsuario(usuario);
        perfiles.stream().forEach(perfil -> {
            List<SgdRolPerfil> rolesPerfil = new ArrayList<>();
            try {
                rolesPerfil = sgdRolPerfilService.findAllBySgdPerfil(perfil.getSgdPerfil());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            rolesPerfil.stream().forEach(rol -> {
                authorities.add(new SimpleGrantedAuthority(rol.getSgdRol().getCtgCatalogoNombre()));
            });
        });
        return authorities;
    }

    public CustomUser getCustomUser(CustomUser customUser, String username) throws Exception {
        //
        return customUser;
    }

    public Map login(Login loginDto) {
        CustomUser customUser = null;
        try{
        SgdUsuario usuario = sgdUsuarioService.findBySgdUsuarioUsername(loginDto.getUsername());
        if(usuario.getSgdUsuarioActivo().equals("1")){
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            GstLog gstLog = new GstLog();
            gstLog = gstLogService.crearGstLog("LOGIN", loginDto.getIpAddress());
            logger.info("Sesion iniciada Usuario: " + usuario.getSgdUsuarioUsername());
            //actualizar flag logueado
            sgdUsuarioService.actualizarSgdUsuarioLogueado(usuario.getSgdUsuarioUsername(),1);
            customUser = loadUserByUsername(loginDto.getUsername());
            if(customUser.getAuthorities() == null || customUser.getAuthorities().size() == 0)
                throw new MyServiceException("111111", "Error usuario sin ningun perfil activo asociado!");
            String jwtToken = jwtTokenUtils.generateToken(customUser);
            customUser.setToken(jwtToken);
            customUser.setUserId(usuario.getSgdUsuarioId());
            customUser.setUserName(usuario.getSgdUsuarioUsername());
            customUser.setAgencia(usuario.getCtgAgencia().getCtgAgenciaDescripcion());

            SgdUsuarioToken sgdUsuarioToken = new SgdUsuarioToken();
            sgdUsuarioToken.setSgdUsuarioClave(MyUtils.getBase64Encode(customUser.getPassword()));
            sgdUsuarioToken.setSgdUsuarioId(customUser.getUserName());
            sgdUsuarioToken.setSgdUsuarioUsername(customUser.getFullName());
            sgdUsuarioToken.setSgdUsuarioAuthorization(jwtToken);
            sgdUsuarioTokenService.update(sgdUsuarioToken);
        }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        //
        Map resp = new LinkedHashMap();
        resp.put("response", customUser);
        resp.put("code", "00");
        resp.put("message", "Successful operation");
        return resp;
    }

}
