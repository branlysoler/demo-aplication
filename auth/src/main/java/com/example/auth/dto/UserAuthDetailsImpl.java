package com.example.auth.dto;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.auth.entity.UserAuth;


public class UserAuthDetailsImpl implements UserDetails{

    private UserAuth userAuth;

    
    public UserAuthDetailsImpl(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    //lista de GrantedAutority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return userAuth.getPass();
    }

    @Override
    public String getUsername() {
        return userAuth.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userAuth.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userAuth.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userAuth.isActive();
    }

    @Override
    public boolean isEnabled() {
        return userAuth.isActive();
    }

    public String getName() {
        return userAuth.getName();
    }
    
}