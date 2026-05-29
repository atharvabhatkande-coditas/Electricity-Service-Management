package com.coditas.electricityservicemanagement.tenant.entity;

import com.coditas.electricityservicemanagement.tenant.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tenant_users")
public class TenantUsers implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles;

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(roleType ->new SimpleGrantedAuthority("ROLE_"+roleType.name())).toList();

    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
}
