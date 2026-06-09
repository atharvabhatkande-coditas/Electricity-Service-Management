package com.coditas.electricityservicemanagement.common.security;

import com.coditas.electricityservicemanagement.common.config.TenantContext;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.repository.PlatformUserRepository;
import com.coditas.electricityservicemanagement.tenant.repository.TenantUserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final PlatformUserRepository platformUserRepository;
    private final TenantUserRepository tenantUserRepository;
    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) {
        if(!Objects.equals(TenantContext.getCurrentSchema(),"public")){
            return tenantUserRepository.findByUsername(username)
                    .orElseThrow(()->new NotFoundException(USER_NOT_FOUND));
        }
        else{
            return platformUserRepository.findByUsername(username)
                    .orElseThrow(()->new NotFoundException(USER_NOT_FOUND));
        }

    }
}
