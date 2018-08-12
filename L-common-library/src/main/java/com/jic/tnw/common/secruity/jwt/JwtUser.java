package com.jic.tnw.common.secruity.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

public class JwtUser implements UserDetails {
    private final String id;
    private final String username;
    private final String lumobile;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final LocalDateTime lastPasswordResetTime;

    public JwtUser(
            String id,
            String lumobile,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            LocalDateTime lastPasswordResetTime) {
        this.id = id;
        this.lumobile = lumobile;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.lastPasswordResetTime = lastPasswordResetTime;
    }

    @JsonIgnore
    public String getLumobile() {
        return lumobile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public LocalDateTime getLastPasswordResetTime() {
        return lastPasswordResetTime;
    }
}
