package com.coditas.electricityservicemanagement.tenant.service;

import com.coditas.electricityservicemanagement.common.config.TenantContext;
import com.coditas.electricityservicemanagement.common.dto.request.LoginRequest;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.util.JwtUtil;
import com.coditas.electricityservicemanagement.platform.dto.request.RefreshTokenRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.AccessTokenResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.LoginResponseTokens;
import com.coditas.electricityservicemanagement.platform.dto.response.LogoutResponse;
import com.coditas.electricityservicemanagement.platform.entity.Invitation;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.entity.TenantRefreshToken;
import com.coditas.electricityservicemanagement.platform.repository.InvitationRepository;
import com.coditas.electricityservicemanagement.platform.repository.TenantRefreshTokenRepository;
import com.coditas.electricityservicemanagement.tenant.dto.request.TenantRegisterRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.TenantRegisterResponse;
import com.coditas.electricityservicemanagement.tenant.entity.TenantUsers;
import com.coditas.electricityservicemanagement.tenant.enums.RoleType;

import com.coditas.electricityservicemanagement.tenant.repository.TenantUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.*;

@Service
@RequiredArgsConstructor
public class TenantAuthService {
    private final TenantUserRepository tenantUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final InvitationRepository invitationRepository;
    private final AuthenticationManager authenticationManager;
    private final TenantRefreshTokenRepository tenantRefreshTokenRepository;
    private final JwtUtil jwtUtil;
    @Value("${jwt.accessExpiration}")
    private long accessExpiration;

    public TenantRegisterResponse registerTenantUser(TenantRegisterRequest request) {
        TenantUsers tenantUser=tenantUserRepository.findByUsername(request.getUsername())
                .orElse(null);

        if(!Objects.isNull(tenantUser)){
            throw new AuthenticationException(USER_EXIST);
        }

        Invitation invitation=invitationRepository.findByEmailAndCode(request.getUsername(),request.getCode())
                .orElseThrow(()->new AuthenticationException(VERIFY_CODE));
        if(!Objects.equals(invitation.getCode(),request.getCode())){
            throw new AuthenticationException(VERIFY_CODE);
        }
        if(invitation.getExpireAt().isBefore(Instant.now())){
            throw new AuthenticationException(VERIFY_CODE);
        }
        if(!Objects.equals(TenantContext.getCurrentTenant(),invitation.getTenantId())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        TenantUsers user=TenantUsers.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .isEnabled(true)
                .roles(getALlRoles(invitation.getRole().name()))
                .build();
        tenantUserRepository.save(user);

        return TenantRegisterResponse.builder()
                .message(REGISTRATION_SUCCESS)
                .build();
    }

    public LoginResponseTokens loginTenantUser(LoginRequest request) {
        try{
            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

            TenantUsers tenantUser=(TenantUsers) authentication.getPrincipal();
            if (Objects.isNull(tenantUser)) {
                throw new AuthenticationException(USER_NOT_FOUND);
            }

            return jwtUtil.generateTenantTokens(tenantUser,TenantContext.getCurrentTenant());

        }catch (Exception e){
            throw new AuthenticationException(LOGIN_FAILURE);
        }

    }


    @Transactional
    public LogoutResponse logoutUser(TenantUsers tenantUser) {
        TenantRefreshToken refreshToken=tenantRefreshTokenRepository.findByUsername(tenantUser.getUsername())
                .orElseThrow(()->new AuthenticationException(SESSION_EXPIRED));
        refreshToken.setToken(null);
        tenantRefreshTokenRepository.save(refreshToken);
        return LogoutResponse
                .builder()
                .message(LOGOUT)
                .build();
    }

    public AccessTokenResponse generateAccessToken(RefreshTokenRequest request, TenantUsers tenantUser) {

        TenantRefreshToken refreshToken=tenantRefreshTokenRepository.findByUsername(tenantUser.getUsername())
                .orElse(new TenantRefreshToken());

        if(!request.getRefreshToken().equals(refreshToken.getToken())){
            throw new AuthenticationException(SESSION_EXPIRED);
        }

        List<String> roles=tenantUser.getRoles().stream().map(Enum::name).toList();

        String accessToken=jwtUtil.generateTokenInternal(tenantUser.getUsername(),roles,accessExpiration,"access",TenantContext.getCurrentTenant());
        return AccessTokenResponse
                .builder()
                .accessToken(accessToken)
                .build();
    }

    private Set<RoleType>getALlRoles(String role){
        Set<RoleType>roles=new HashSet<>();
        if(Objects.equals(role,RoleType.POC.name())){
            roles.add(RoleType.POC);
            roles.add(RoleType.OPERATIONAL_HEAD);
            roles.add(RoleType.HIGHER_MANAGER);
            roles.add(RoleType.MANAGER);
            roles.add(RoleType.PERSONNEL);
        }else if(Objects.equals(role,RoleType.OPERATIONAL_HEAD.name())){
            roles.add(RoleType.OPERATIONAL_HEAD);
            roles.add(RoleType.HIGHER_MANAGER);
            roles.add(RoleType.MANAGER);
            roles.add(RoleType.PERSONNEL);
        }else if(Objects.equals(role,RoleType.HIGHER_MANAGER.name())){
            roles.add(RoleType.HIGHER_MANAGER);
            roles.add(RoleType.MANAGER);
            roles.add(RoleType.PERSONNEL);
        }else if(Objects.equals(role,RoleType.MANAGER.name())){
            roles.add(RoleType.MANAGER);
            roles.add(RoleType.PERSONNEL);
        }else if(Objects.equals(role,RoleType.PERSONNEL.name())){
            roles.add(RoleType.PERSONNEL);

        }
        return roles;
    }

}
