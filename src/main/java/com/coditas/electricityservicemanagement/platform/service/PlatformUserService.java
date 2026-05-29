package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.repository.PlatformUserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PlatformUserService implements UserDetailsService {
    private final PlatformUserRepository platformUserRepository;
    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username){
        return platformUserRepository.findByUsername(username)
                .orElseThrow(()->new NotFoundException(USER_NOT_FOUND));
    }
}
