package com.jarkial.login.configuration.utils;

import com.jarkial.login.model.dto.sgd.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtTokenUtils implements Serializable{

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    // private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jarkial.jwt.secret-key}")
    private String jwtSecret;

    @Value("${jarkial.jwt.token-lifetime}")
    private Long jwtTokenLifeTime;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernamefromToken(String token){ return getClaimFromToken(token, Claims::getSubject);}

    public Date getExpirationDateFromToken(String token){ return getClaimFromToken(token, Claims::getExpiration);}

    public <C> C getClaimFromToken(String token, Function<Claims, C> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token){
        //chuequear posible fix de implementacion deprecated
        // Claims claims = Jwts.parserBuilder().build()
        Claims claims = Jwts.parser()
    .setSigningKey(getSigningKey())
    .parseClaimsJws(token)
    .getBody();
    return claims;
    //    return Jwts.parserBuilder().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public List<String> getDataRolesClaims(Claims claims){
        if(claims != null){
            try{
                List<String> roles = MyUtils.OBJECT_MAPPER.convertValue(claims.get("data"), List.class);
                return roles;
            }catch(Exception exception){
                logger.error("Error transformando data del json: [" + claims.get("data") + "]", exception);
            }
        }
        return null;
    }

    private boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private boolean ignoreTokenExpiration(String token){
        //aqui se especifican los tokens que se quiere ignorar su expiracion
        return false;
    }

    public String generateToken(CustomUser userDetails){
        List<String> data = new ArrayList<>();
        userDetails.getAuthorities().forEach( rol -> data.add(rol.getAuthority()));
        return doGenerateToken(data, userDetails.getUsername());
    }

    private String doGenerateToken(List<String> data, String subject){
        String id = MyUtils.getTransactionId();        
        return Jwts.builder()
        .setId(id).claim("data", data).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(Long.sum(System.currentTimeMillis(), jwtTokenLifeTime)))
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernamefromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}