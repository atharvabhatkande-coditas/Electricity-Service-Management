package com.coditas.electricityservicemanagement.tenant.service;

import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.tenant.repository.TenantUserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TenantUserService implements UserDetailsService {
    private final TenantUserRepository tenantUserRepository;
    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) {
        return tenantUserRepository.findByUsername(username)
                .orElseThrow(()->new NotFoundException(USER_NOT_FOUND));
    }
}
