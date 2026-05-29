package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.dto.request.LoginRequest;
import com.coditas.electricityservicemanagement.common.util.JwtUtil;
import com.coditas.electricityservicemanagement.platform.dto.response.LoginResponseTokens;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.platform.repository.PlatformUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.LOGIN_FAILURE;
import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PlatformUserRepository platformUserRepository;
    private final AuthenticationManager authenticationManagerPlatform;
    private final JwtUtil jwtUtil;

    public LoginResponseTokens loginPlatformUser(LoginRequest request) {
        try{
            Authentication authentication=authenticationManagerPlatform
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

}
