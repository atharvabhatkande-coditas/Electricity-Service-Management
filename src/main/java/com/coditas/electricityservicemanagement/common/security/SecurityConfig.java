package com.coditas.electricityservicemanagement.common.security;

import com.coditas.electricityservicemanagement.common.filter.JwtFilter;

import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.coditas.electricityservicemanagement.common.constants.EndPoints.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER1,SWAGGER2,SWAGGER3).permitAll()
                        .requestMatchers(AUTH).permitAll()
                        .requestMatchers(AUTH_TENANT).permitAll()

                        //management
                        .requestMatchers(HttpMethod.POST,STATE).hasRole(RoleType.MANAGEMENT.name())
                        .requestMatchers(HttpMethod.GET,STATE_MANAGEMENT).hasRole(RoleType.MANAGEMENT.name())

                        .requestMatchers(HttpMethod.GET,DISTRICT_MANAGEMENT).hasRole(RoleType.MANAGEMENT.name())

                        .requestMatchers(HttpMethod.GET,CITY_MANAGEMENT).hasRole(RoleType.MANAGEMENT.name())


                        //stateHead
                        .requestMatchers(HttpMethod.GET,STATE_STATE_HEAD).hasRole(RoleType.STATE_HEAD.name())
                        .requestMatchers(HttpMethod.POST,DISTRICT).hasRole(RoleType.STATE_HEAD.name())
                        .requestMatchers(HttpMethod.GET,DISTRICT_STATE_HEAD).hasRole(RoleType.STATE_HEAD.name())


                        .requestMatchers(HttpMethod.GET,CITY_STATE_HEAD).hasRole(RoleType.STATE_HEAD.name())


                        //districtHead
                        .requestMatchers(HttpMethod.GET,DISTRICT).hasRole(RoleType.DISTRICT_HEAD.name())
                        .requestMatchers(HttpMethod.POST,CITY).hasRole(RoleType.DISTRICT_HEAD.name())

                        .requestMatchers(HttpMethod.GET,CITY_DISTRICT_HEAD).hasRole(RoleType.DISTRICT_HEAD.name())

                        //cityHead
                        .requestMatchers(HttpMethod.GET,CITY).hasRole(RoleType.CITY_HEAD.name())



                        //crm_agent
                        .requestMatchers(INVITATION).hasRole(RoleType.CRM_AGENT.name())

                        //sales
                        .requestMatchers(TENANT).hasRole(RoleType.SALES.name())
                        .anyRequest().authenticated()


                )
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    @Primary
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

}