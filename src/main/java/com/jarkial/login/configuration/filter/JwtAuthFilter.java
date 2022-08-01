package com.jarkial.login.configuration.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jarkial.login.configuration.security.SgdUsuarioDetailsServiceImpl;
import com.jarkial.login.configuration.utils.JwtTokenUtils;
import com.jarkial.login.model.dto.OutResponse;
import com.jarkial.login.model.dto.sgd.CustomUser;
import com.jarkial.login.model.entity.sgd.SgdUsuarioToken;
import com.jarkial.login.repositories.sgd.SgdUsuarioTokenRepository;
import com.jarkial.login.services.sgd.SgdUsuarioTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    SgdUsuarioTokenRepository sgdUsuarioTokenRepository;

    @Autowired
    SgdUsuarioDetailsServiceImpl sgdUsuarioDetailsServiceImpl;

    private static final String variableResponse = "responseDTO";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("token");
        request.setCharacterEncoding("UTF-8");
        String[] resourcePath = request.getServletPath().split("/");
        String resourceName = null;
        if (resourcePath.length > 0) {
            resourceName = resourcePath[1];
        }
        List<String> rolesUsuario = null;
        String jwtToken = null;
        try {
            jwtToken = StringUtils.isBlank(requestTokenHeader) ? null : requestTokenHeader;
            String regex = "[1-z0-9]+\\.[1-z0-9]+\\.(js|css|woff|woff2|ttf)+";
            Pattern patron = Pattern.compile(regex);
            if (!StringUtils.equals(resourceName, "security") && resourceName != null
                    && !StringUtils.equals(resourceName, "assets") && !patron.matcher(resourceName).find()
                    && !StringUtils.equals(resourceName, "favicon.ico")) {
                if (jwtToken == null) {
                    logger.warn("TOKEN NO ENVIADO");
                    OutResponse outResponse = new OutResponse();
                    outResponse.setCode(OutResponse.COD_JWT_MISSING);
                    outResponse.setMessage(OutResponse.MSG_JWT_MISSING);
                    request.setAttribute(variableResponse, outResponse);
                    request.getRequestDispatcher("/security/unathorized").forward(request, response);
                    return;
                }
                Claims claims = jwtTokenUtils.getAllClaimsFromToken(jwtToken);
                rolesUsuario = jwtTokenUtils.getDataRolesClaims(claims);
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (rolesUsuario != null && StringUtils.isNotBlank(claims.getSubject())) {
                    rolesUsuario.stream().forEach(rol -> authorities.add(new SimpleGrantedAuthority(rol)));
                    String userName = jwtTokenUtils.getUsernamefromToken(jwtToken);
                    SgdUsuarioToken sgdUsuarioToken = sgdUsuarioTokenRepository.findById(userName).orElse(null);
                    if (sgdUsuarioToken != null) {
                        CustomUser userDetails = sgdUsuarioDetailsServiceImpl.loadUserByUsername(userName);
                        if (jwtTokenUtils.validateToken(jwtToken, userDetails)) {
                            logger.info("ACTUALIZANDO TOKEN");
                            String newJwtToken = jwtTokenUtils.generateToken(userDetails);
                            response.addHeader("token", newJwtToken);
                            sgdUsuarioToken.setSgdUsuarioId(userDetails.getUsername());
                            sgdUsuarioToken.setSgdUsuarioAuthorization(newJwtToken);
                            sgdUsuarioToken.setSgdUsuarioUsername(userDetails.getUsername());
                            sgdUsuarioTokenRepository.saveAndFlush(sgdUsuarioToken);
                            logger.info("TOKEN VALIDO");
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            usernamePasswordAuthenticationToken
                                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        } else {
                            logger.warn("INVALID TOKEN");
                            OutResponse outResponse = new OutResponse();
                            outResponse.setCode(OutResponse.COD_JWT_INVALID);
                            outResponse.setMessage(OutResponse.MSG_JWT_INVALID);
                            request.setAttribute(variableResponse, outResponse);
                            request.getRequestDispatcher("/security/unathorized").forward(request, response);
                        }
                    } else {
                        logger.error("USUARIO YA SE ENCUENTRA LOGEADO");
                        OutResponse outResponse = new OutResponse();
                        outResponse.setCode(OutResponse.COD_JWT_SESSION_DUPLICATE);
                        outResponse.setMessage(OutResponse.MSG_JWT_SESSION_DUPLICATE);
                        request.setAttribute(variableResponse, outResponse);
                        request.getRequestDispatcher("/security/unathorized").forward(request, response);
                    }
                }
            }
        } catch (ExpiredJwtException exception) {
            logger.error("TOKEN EXPIRED");
            OutResponse outResponse = new OutResponse();
            outResponse.setCode(OutResponse.COD_JWT_EXPIRED);
            outResponse.setMessage(OutResponse.MSG_JWT_EXPIRED);
            request.setAttribute(variableResponse, outResponse);
            request.getRequestDispatcher("/security/unathorized").forward(request, response);
        } catch (SignatureException exception) {
            logger.error("INVALID TOKEN");
            OutResponse outResponse = new OutResponse();
            outResponse.setCode(OutResponse.COD_JWT_INVALID);
            outResponse.setMessage(OutResponse.MSG_JWT_INVALID);
            request.setAttribute(variableResponse, outResponse);
            request.getRequestDispatcher("/security/unathorized").forward(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}