package com.jarkial.login.configuration.security;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jarkial.login.configuration.utils.JwtTokenUtils;
import com.jarkial.login.configuration.utils.MyUtils;
import com.jarkial.login.model.dto.sgd.CustomUser;
import com.jarkial.login.model.dto.sgd.Login;
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
import com.jarkial.login.webservices.sgd.SgdUsuarioServiceWeb;

@Service
public class SgdUsuarioDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SgdUsuarioService sgdUsuarioService;

    @Autowired
    SgdUsuarioServiceWeb sgdUsuarioServiceWeb;

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
        SgdUsuario sgdUsuario = sgdUsuarioService.findBySgdUsuarioUsername(username);
        if(sgdUsuario.getSgdUsuarioActivo().equals("0"))
            throw new MyServiceException("Atencion: El usuario esta desactivado");
        String primerNombre = sgdUsuario.getSgdUsuarioPrimerNombre();
        String segundoNombre = sgdUsuario.getSgdUsuarioSegundoNombre();
        String primerApellido = sgdUsuario.getSgdUsuarioPrimerApellido();
        String segundoApellido = sgdUsuario.getSgdUsuarioSegundoApellido();
        String nombreCompleto = ((primerNombre!=null)? primerNombre : "") + " " + ((segundoNombre!=null)? segundoNombre : "")+ " " + ((primerApellido!=null)? primerApellido : "") + " " + ((segundoApellido!=null)? segundoApellido : "");
        customUser.setUserId(sgdUsuario.getSgdUsuarioId());
        customUser.setFullName(nombreCompleto);
        customUser.setAgenciaId(sgdUsuario.getCtgAgencia().getCtgAgenciaId());
        customUser.setAgencia(sgdUsuario.getCtgAgencia().getCtgAgenciaDescripcion());
        customUser.setSubTipoAgenciaId(sgdUsuario.getCtgAgencia().getCtgSubTipoAgencia().getCtgSubTipoAgenciaId());
        customUser.setSubTipoAgencia(sgdUsuario.getCtgAgencia().getCtgSubTipoAgencia().getCtgSubTipoAgenciaDescripcion());
        customUser.setTipoAgenciaId(sgdUsuario.getCtgAgencia().getCtgSubTipoAgencia().getCtgTipoAgencia().getCtgTipoAgenciaId());
        customUser.setTipoAgencia(sgdUsuario.getCtgAgencia().getCtgSubTipoAgencia().getCtgTipoAgencia().getCtgTipoAgenciaDescripcion());
        customUser.setUser(sgdUsuario.getSgdUsuarioUsername());
        customUser.setRequiredChangePassword("0");
        return customUser;
    }

    public Map login(Login loginDto) throws MyServiceException{
        CustomUser customUser = null;
        try{
        SgdUsuario usuario = sgdUsuarioService.findBySgdUsuarioUsername(loginDto.getUsername());
        if(usuario==null){
            throw new MyServiceException("Usuario inexistente");
        }else if(usuario.getSgdUsuarioActivo().equals("1")){
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            GstLog gstLog = new GstLog();
            gstLog = gstLogService.crearGstLog("LOGIN", loginDto.getIpAddress());
            logger.info("Sesion iniciada Usuario: " + usuario.getSgdUsuarioUsername());
            sgdUsuarioServiceWeb.actualizarSgdUsuarioLogueado(usuario.getSgdUsuarioUsername(),1);
            customUser = loadUserByUsername(loginDto.getUsername());
            if(customUser.getAuthorities() == null || customUser.getAuthorities().size() == 0)
                throw new MyServiceException("111111", "Error usuario sin ningun perfil activo asociado!");
            String jwtToken = jwtTokenUtils.generateToken(customUser);
            customUser.setToken(jwtToken);
            customUser.setUserId(usuario.getSgdUsuarioId());
            //revisar carga del username en user details
            //customUser.setUsername(usuario.getSgdUsuarioUsername());
            customUser.setAgencia(usuario.getCtgAgencia().getCtgAgenciaDescripcion());

            SgdUsuarioToken sgdUsuarioToken = new SgdUsuarioToken();
            sgdUsuarioToken.setSgdUsuarioClave(MyUtils.getBase64Encode(customUser.getPassword()));
            sgdUsuarioToken.setSgdUsuarioId(usuario.getSgdUsuarioUsername());
            sgdUsuarioToken.setSgdUsuarioUsername(customUser.getFullName());
            sgdUsuarioToken.setSgdUsuarioAuthorization(jwtToken);
            sgdUsuarioTokenService.update(sgdUsuarioToken);
        }
        }catch(Exception exception){
            throw new MyServiceException(exception);
        }
        Map resp = new LinkedHashMap();
        resp.put("response", customUser);
        resp.put("code", "00");
        resp.put("message", "Successful operation");
        return resp;
    }

}
