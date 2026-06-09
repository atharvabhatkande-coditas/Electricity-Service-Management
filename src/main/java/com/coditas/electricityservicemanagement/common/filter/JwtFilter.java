package com.coditas.electricityservicemanagement.common.filter;

import com.coditas.electricityservicemanagement.common.config.TenantContext;
import com.coditas.electricityservicemanagement.common.config.TenantSchemaResolver;
import com.coditas.electricityservicemanagement.common.security.CustomUserDetailsService;
import com.coditas.electricityservicemanagement.common.util.JwtUtil;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.ErrorResponse;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.service.PlatformUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.UNAUTHORIZED;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final TenantSchemaResolver tenantSchemaResolver;
    private final CustomUserDetailsService  customUserDetailsService;
    @Override
    @NullMarked
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header=request.getHeader("Authorization");
        String username=null;
        String token=null;
        String tenantId=null;

        String headerTenantId=request.getHeader("X-TENANT-NAME");
        if(headerTenantId==null || headerTenantId.isBlank()){
            TenantContext.setCurrentSchema("public");
        }else{
            TenantContext.setCurrentTenant(headerTenantId);
            String schemaName=tenantSchemaResolver.getTenantSchema(headerTenantId);
            TenantContext.setCurrentSchema(schemaName);
        }

        if(header!=null && header.startsWith("Bearer ")){
            token=header.substring(7);
            try{
                username=jwtUtil.extractUsername(token);
                tenantId=jwtUtil.extractTenantIdFromToken(token);

                if(tenantId!=null){
                    TenantContext.setCurrentTenant(tenantId);
                    String schemaName=tenantSchemaResolver.getTenantSchema(tenantId);
                    TenantContext.setCurrentSchema(schemaName);
                }

            }catch (JwtException e){
                response.setStatus(401);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorResponse errorResponse=new ErrorResponse(UNAUTHORIZED,401);
                ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
                response.getWriter().write(objectMapper.writeValueAsString(applicationResponse));
                return;
            }
        }



        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            if(jwtUtil.extractTypeFromToken(token).equals("refresh")){
                response.setStatus(401);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorResponse errorResponse=new ErrorResponse(UNAUTHORIZED,401);
                ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
                response.getWriter().write(objectMapper.writeValueAsString(applicationResponse));
                return;
            }
            try{
                UserDetails userDetails =customUserDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(userDetails,username,token)){
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }catch (NotFoundException e){
                response.setStatus(401);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorResponse errorResponse=new ErrorResponse(UNAUTHORIZED,401);
                ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
                response.getWriter().write(objectMapper.writeValueAsString(applicationResponse));
                return;
            }

        }

        filterChain.doFilter(request,response);
        TenantContext.clear();

    }

}
