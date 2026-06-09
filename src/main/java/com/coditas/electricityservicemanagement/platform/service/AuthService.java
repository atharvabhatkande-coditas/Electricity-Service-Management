package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.dto.request.LoginRequest;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.platform.dto.request.RefreshTokenRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.RegisterRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.AccessTokenResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.LogoutResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.RegisterResponse;
import com.coditas.electricityservicemanagement.platform.entity.Invitation;
import com.coditas.electricityservicemanagement.platform.entity.RefreshToken;
import com.coditas.electricityservicemanagement.platform.repository.InvitationRepository;
import com.coditas.electricityservicemanagement.common.util.JwtUtil;
import com.coditas.electricityservicemanagement.platform.dto.response.LoginResponseTokens;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.platform.repository.PlatformUserRepository;
import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import com.coditas.electricityservicemanagement.platform.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
public class AuthService {
    private final PlatformUserRepository platformUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final InvitationRepository invitationRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.accessExpiration}")
    private long accessExpiration;

    public LoginResponseTokens loginPlatformUser(LoginRequest request) {
        try{
            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

            PlatformUsers platformUser=(PlatformUsers) authentication.getPrincipal();
            if (Objects.isNull(platformUser)) {
                throw new AuthenticationException(USER_NOT_FOUND);
            }

            return jwtUtil.generatePlatformTokens(platformUser);

        }catch (Exception e){
            throw new AuthenticationException(LOGIN_FAILURE);
        }

    }
    @Transactional
    public RegisterResponse registerPlatformUser(@Valid RegisterRequest registerRequest) {
        PlatformUsers platformUser=platformUserRepository.findByUsername(registerRequest.getUsername())
                .orElse(null);
        if(!Objects.isNull(platformUser)){
            throw new AlreadyExistException(USER_EXIST);
        }
        Invitation invitation=invitationRepository.findByEmailAndCode(registerRequest.getUsername(), registerRequest.getCode())
                .orElseThrow(()->new AuthenticationException(VERIFY_CODE));

        if(!Objects.equals(invitation.getCode(),registerRequest.getCode())){
            throw new AuthenticationException(VERIFY_CODE);
        }
        if(invitation.getExpireAt().isBefore(Instant.now())){
            throw new AuthenticationException(VERIFY_CODE);
        }

        PlatformUsers newUser=PlatformUsers.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isEnabled(true)
                .roles(getALlRoles(invitation.getRole()))
                .build();
        platformUserRepository.save(newUser);

        return RegisterResponse.builder()
                .message(REGISTRATION_SUCCESS)
                .build();

    }
    @Transactional
    public LogoutResponse logoutUser(PlatformUsers platformUser) {
        RefreshToken refreshToken=refreshTokenRepository.findByUsername(platformUser.getUsername())
                .orElseThrow(()->new AuthenticationException(SESSION_EXPIRED));
        refreshToken.setToken(null);
        refreshTokenRepository.save(refreshToken);
        return LogoutResponse
                .builder()
                .message(LOGOUT)
                .build();
    }
    public AccessTokenResponse generateAccessToken(RefreshTokenRequest request,PlatformUsers platformUser) {

        RefreshToken refreshToken=refreshTokenRepository.findByUsername(platformUser.getUsername())
                .orElse(new RefreshToken());

        if(!request.getRefreshToken().equals(refreshToken.getToken())){
            throw new AuthenticationException(SESSION_EXPIRED);
        }

        List<String> roles=platformUser.getRoles().stream().map(Enum::name).toList();

        String accessToken=jwtUtil.generateTokenInternal(platformUser.getUsername(),roles,accessExpiration,"access","public");
        return AccessTokenResponse
                .builder()
                .accessToken(accessToken)
                .build();
    }


    private Set<RoleType> getALlRoles(RoleType role){
        Set<RoleType>roles=new HashSet<>();

        if(role==RoleType.MANAGEMENT){
            roles.add(RoleType.MANAGEMENT);
            roles.add(RoleType.SALES);
            roles.add(RoleType.STATE_HEAD);
            roles.add(RoleType.DISTRICT_HEAD);
            roles.add(RoleType.CITY_HEAD);
            roles.add(RoleType.CRM_AGENT);
            roles.add(RoleType.TECHNICIAN);
            roles.add(RoleType.BILLER);
            roles.add(RoleType.CUSTOMER);
        }else if(role==RoleType.SALES){
            roles.add(RoleType.SALES);
            roles.add(RoleType.STATE_HEAD);
            roles.add(RoleType.DISTRICT_HEAD);
            roles.add(RoleType.CITY_HEAD);
            roles.add(RoleType.CRM_AGENT);
            roles.add(RoleType.TECHNICIAN);
            roles.add(RoleType.BILLER);
            roles.add(RoleType.CUSTOMER);
        }else if(role==RoleType.STATE_HEAD){
            roles.add(RoleType.STATE_HEAD);
            roles.add(RoleType.DISTRICT_HEAD);
            roles.add(RoleType.CITY_HEAD);
            roles.add(RoleType.CRM_AGENT);
            roles.add(RoleType.TECHNICIAN);
            roles.add(RoleType.BILLER);
            roles.add(RoleType.CUSTOMER);
        }else if(role==RoleType.DISTRICT_HEAD){
            roles.add(RoleType.DISTRICT_HEAD);
            roles.add(RoleType.CITY_HEAD);
            roles.add(RoleType.CRM_AGENT);
            roles.add(RoleType.TECHNICIAN);
            roles.add(RoleType.BILLER);
            roles.add(RoleType.CUSTOMER);
        }else if(role==RoleType.CITY_HEAD){
            roles.add(RoleType.CITY_HEAD);
            roles.add(RoleType.CRM_AGENT);
            roles.add(RoleType.TECHNICIAN);
            roles.add(RoleType.BILLER);
            roles.add(RoleType.CUSTOMER);
        }else if(role==RoleType.CRM_AGENT){
            roles.add(RoleType.CRM_AGENT);
        }else if(role==RoleType.TECHNICIAN){
            roles.add(RoleType.TECHNICIAN);
        }else if(role==RoleType.BILLER){
            roles.add(RoleType.BILLER);
        }else if(role==RoleType.CUSTOMER){
            roles.add(RoleType.CUSTOMER);
        }
        return roles;
    }
}
