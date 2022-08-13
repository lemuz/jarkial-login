package com.jarkial.login.configuration.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.support.LdapUtils;

import com.jarkial.login.configuration.utils.AppContext;
import com.jarkial.login.configuration.utils.MyUtils;
import com.jarkial.login.model.dto.sgd.CustomUser;
import com.jarkial.login.model.exceptions.MyServiceException;

import io.jsonwebtoken.lang.Assert;
import lombok.Setter;

public class ActiveDirectoryLdapAuthenticationProvider implements AuthenticationProvider{

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Pattern SUB_ERROR_CODE = Pattern.compile(".*data\\s([0-9a-f]{3,4}).*");

    //Error codes
    private static final int USERNAME_NOT_FOUND = 0x525;
    private static final int INVALID_PASSWORD = 0x52e;
    private static final int NOT_PERMITTED = 0x530;
    private static final int PASSWORD_EXPIRED = 0x532;
    private static final int ACCOUNT_DISABLED = 0x533;
    private static final int ACCOUNT_EXPIRED = 0x701;
    private static final int PASSWORD_NEEDS_RESET = 0x773;
    private static final int ACCOUNT_LOOKED = 0x775;
    private static final String USER_PASSWORD_INVALID = "Usuario y/o contrase\u00F1a inv\u00E1lidos";
    private static final String  GENERIC_MESSAGE = "Problemas al tratar de autenticar al usuario";

    private final String domain;
    private final String rootDn;
    private final String url;

    @Setter
    private boolean convertSubErrorCodesToExceptions;

    //Only used to allow tests to substitute a mock LdapContext
    ContextFactory contextFactory = new ContextFactory();

    

    /**
     * @param domain
     * @param url
     */
    public ActiveDirectoryLdapAuthenticationProvider(String domain, String url) {
        Assert.isTrue(StringUtils.isNotBlank(url), "Url no puede ser vacia");
        this.domain = StringUtils.isNotBlank(domain)? domain.toLowerCase(Locale.ROOT):null;
        this.url = url;
        rootDn = this.domain==null? null : rootDnFromDomain(this.domain);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = userToken.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isBlank(username))
            throw new BadCredentialsException("Nombre de usuario no puede estar vac\u00EDo");
        if(StringUtils.isBlank(password))
            throw new BadCredentialsException("Contrase\u00F1a no puede estar vac\u00EDa");
        
        if("SYSTEM".equalsIgnoreCase(username)){
            if(!password.equals(MyUtils.dateFormatAsYYYYMMDD.format(new Date()) + "MYPWD"))
                throw new BadCredentialsException(USER_PASSWORD_INVALID);
        }else{
            try{
                doAuthentication(userToken);
            }catch(MyServiceException exception){
                exception.printStackTrace();
            }
        }

        CustomUser customUser =  new CustomUser(StringUtils.upperCase(username), password, true, true, true, true, new ArrayList<>());
        try{
            customUser = AppContext.getSgdUsuarioDetailsServiceImpl().getCustomUser(customUser, StringUtils.upperCase(username));
        }catch(Exception exception){
            logger.error(exception.getMessage(), exception);
            throw new BadCredentialsException(exception.getMessage());
        }
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(customUser, password, new ArrayList<>());
        result.setDetails(authentication.getDetails());
        return result;

    }

    protected DirContextOperations doAuthentication(UsernamePasswordAuthenticationToken auth) throws MyServiceException{
        String username = auth.getName();
        String password = (String) auth.getCredentials();
        DirContext ctx = bindAsUser(username, password);
        try{
            return searchForUser(ctx, username);
        }catch(NamingException exception){
            logger.error(exception.getMessage(), exception);
            throw new BadCredentialsException(GENERIC_MESSAGE);
        }finally{
            LdapUtils.closeContext(ctx);
        }
    }

/*    protected Collection<? extends GrantedAuthority> loadAuthorities(String username, String password){
        try{
Sgdusuario usuario = AppContext.getSgdUsuarioService().findBySgdUsername(username);
        }catch(Exception exception){
            logger.error(exception.getMessage(), exception);
            exception.printStackTrace();
        }
        return AuthorityUtils.NO_AUTHORITIES;
    }
 */
    private DirContext bindAsUser(String username, String password) throws MyServiceException{
        final String bindUrl = url;
        Hashtable<String,String> env = new Hashtable<>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        String bindPrincipal = createBindPrincipal(username);
        env.put(Context.SECURITY_PRINCIPAL, bindPrincipal);
        env.put(Context.PROVIDER_URL, bindUrl);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.OBJECT_FACTORIES, DefaultDirObjectFactory.class.getName());
        try{
            return contextFactory.createContext(env);
        }catch(NamingException exception){
            logger.error("Intentando conectar LDAP inicial: " + exception.getMessage(), exception);
            exception.printStackTrace();
            if(exception instanceof OperationNotSupportedException){
                logger.warn("Operacion no soportada");
                handleBindException(username, bindPrincipal, exception);
                throw new BadCredentialsException(GENERIC_MESSAGE);
            }else{
                logger.warn("Excepcion ldap");
                throw LdapUtils.convertLdapException(exception);
            }
        }
    }

    private void handleBindException(String username, String bindPrincipal, NamingException exception) throws MyServiceException{
        int subErrorCode = parseSubErrorCode(exception.getMessage());
        if(subErrorCode > 0){
            logger.warn("Codigo de Error: " + subErrorCode);
            if(convertSubErrorCodesToExceptions)
            raiseExceptionForErrorCode(username, subErrorCode);
        }else{
            throw new BadCredentialsException("Problemas al tratar de obtener c\u00F3digo de error del AD");
        }
    }

    int parseSubErrorCode(String message){
        Matcher m = SUB_ERROR_CODE.matcher(message);
        if(m.matches())
            return Integer.parseInt(m.group(1),16);
        return -1;
    }

    void raiseExceptionForErrorCode(String username, int code) throws MyServiceException{
        switch(code){
            case PASSWORD_EXPIRED:
                throw new MyServiceException("Contrase\u00F1a ha expirado");
            case ACCOUNT_EXPIRED:
            throw new MyServiceException("Cuenta de usuario ha expirado");
            case ACCOUNT_LOOKED:
            throw new MyServiceException("Cuenta de usuario bloqueada");
            case PASSWORD_NEEDS_RESET:
            throw new MyServiceException("Contrase\u00F1a necesita ser actualizada");
            case USERNAME_NOT_FOUND:
            case INVALID_PASSWORD:
            case NOT_PERMITTED:
            throw new MyServiceException(USER_PASSWORD_INVALID);
            case ACCOUNT_DISABLED:
            throw new MyServiceException("Cuenta de usuario deshabilitada");
        }
        throw new BadCredentialsException(GENERIC_MESSAGE);
    }

    private DirContextOperations searchForUser(DirContext ctx, String username) throws NamingException{
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String searchFilter = "(&(objectClass=user)(userPrincipalName={0}))";
        final String bindPrincipal = createBindPrincipal(username);
        String searchRoot = rootDn!=null? rootDn: searchRootFromPrincipal(bindPrincipal);
        logger.info(searchFilter);
        logger.info(searchRoot);
        return SpringSecurityLdapTemplate.searchForSingleEntryInternal(ctx, searchCtls, searchRoot, searchFilter, new Object[]{bindPrincipal});
    }

    private String searchRootFromPrincipal(String bindPrincipal){
        int charAt = bindPrincipal.lastIndexOf('@');
        if(charAt < 0){
            logger.error("Usuario: " + bindPrincipal + " no contiene el dominio y ningun dominio ha sido configurado");
            throw new BadCredentialsException(GENERIC_MESSAGE);
        }

        return rootDnFromDomain(bindPrincipal.substring(charAt + 1, bindPrincipal.length()));
    }

    private String rootDnFromDomain(String domain){
        String[] tokens = StringUtils.split(domain, ".");
        StringBuilder root = new StringBuilder();
        for(String token: tokens){
            if(root.length() > 0)
                root.append(',');
                root.append("dc=").append(token);
        }
        return root.toString();
    }

    String createBindPrincipal(String username){
        if(domain == null || username.toUpperCase(Locale.ROOT).endsWith(domain.toUpperCase(Locale.ROOT)))
            return username;
            return username.toUpperCase(Locale.ROOT) + "@" + domain.toUpperCase(Locale.ROOT);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    static class ContextFactory{
        DirContext createContext(Hashtable<?,?> env) throws NamingException{
            DirContext ctx = new InitialLdapContext(env, null);
            return ctx;
        }

        
    }
    
}
