package com.coditas.electricityservicemanagement.tenant.service;

import com.coditas.electricityservicemanagement.common.config.TenantContext;
import com.coditas.electricityservicemanagement.common.dto.request.LoginRequest;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.util.JwtUtil;
import com.coditas.electricityservicemanagement.platform.dto.response.LoginResponseTokens;
import com.coditas.electricityservicemanagement.platform.entity.Invitation;
import com.coditas.electricityservicemanagement.platform.repository.InvitationRepository;
import com.coditas.electricityservicemanagement.tenant.dto.request.TenantRegisterRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.TenantRegisterResponse;
import com.coditas.electricityservicemanagement.tenant.entity.TenantUsers;
import com.coditas.electricityservicemanagement.tenant.enums.RoleType;

import com.coditas.electricityservicemanagement.tenant.repository.TenantUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
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
    private final JwtUtil jwtUtil;

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

        Set<RoleType> roles=new HashSet<>();
        if(invitation.getRole()== com.coditas.electricityservicemanagement.platform.enums.RoleType.POC){
            roles.add(RoleType.POC);
            roles.add(RoleType.OPERATIONAL_HEAD);
            roles.add(RoleType.HIGHER_MANAGER);
            roles.add(RoleType.MANAGER);
            roles.add(RoleType.PERSONNEL);
        }

        TenantUsers user=TenantUsers.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .isEnabled(true)
                .roles(roles)
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

}
