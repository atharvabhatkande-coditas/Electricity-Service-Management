package com.coditas.electricityservicemanagement.common.util;


import com.coditas.electricityservicemanagement.platform.dto.response.LoginResponseTokens;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.tenant.entity.TenantUsers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.USER_NOT_FOUND;

@Component
public class JwtUtil {


    private final long refreshExpiration;
    private final long accessExpiration;
    private final SecretKey key;


    public JwtUtil(@Value("${jwt.accessExpiration}")long accessExpiration, @Value("${jwt.refreshExpiration}")long refreshExpiration, @Value("${jwt.secret}") String secret){
        key= Keys.hmacShaKeyFor(secret.getBytes());
        this.accessExpiration=accessExpiration;
        this.refreshExpiration=refreshExpiration;

    }

    public LoginResponseTokens generatePlatformTokens(PlatformUsers platformUser) {
        if(platformUser==null){
            throw new AuthenticationException(USER_NOT_FOUND);
        }
        List<String> roles=platformUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        String tenantId=null;
        if(platformUser.getTenant()==null){
            tenantId="public";
        }else{
            tenantId=platformUser.getTenant().getId();
        }

        String accessToken=generateTokenInternal(platformUser.getUsername(),roles,accessExpiration,"access",tenantId);
        String refreshToken=generateTokenInternal(platformUser.getUsername(),roles,refreshExpiration,"refresh",tenantId);
        return  new LoginResponseTokens(accessToken,refreshToken);
    }

    public LoginResponseTokens generateTenantTokens(TenantUsers tenantUser,String tenantId) {
        if(tenantUser==null){
            throw new AuthenticationException(USER_NOT_FOUND);
        }
        List<String> roles=tenantUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        String accessToken=generateTokenInternal(tenantUser.getUsername(),roles,accessExpiration,"access",tenantId);
        String refreshToken=generateTokenInternal(tenantUser.getUsername(),roles,refreshExpiration,"refresh",tenantId);
        return  new LoginResponseTokens(accessToken,refreshToken);
    }

    public String generateTokenInternal(String username, List<String> roles,long expirationTime,String type,String tenantId){
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("TaskManagementApp")
                .setIssuedAt(new Date())
                .claim("roles",roles)
                .claim("type",type)
                .claim("tenant_id",tenantId)
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractTypeFromToken(String token){
        return extractClaims(token).get("type",String.class);
    }

    public String extractTenantIdFromToken(String token){
        return extractClaims(token).get("tenant_id",String.class);
    }


    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(UserDetails userDetails,String username,String token){
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    public boolean isExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }
}
