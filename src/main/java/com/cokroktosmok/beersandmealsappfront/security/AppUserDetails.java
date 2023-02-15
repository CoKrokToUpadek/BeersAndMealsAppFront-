package com.cokroktosmok.beersandmealsappfront.security;

import com.cokroktosmok.beersandmealsappfront.data.dto.user.UserCredentialsDto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Arrays;
import java.util.Collection;


public class AppUserDetails implements UserDetails {
    private final UserCredentialsDto userEntity;

    public AppUserDetails(UserCredentialsDto userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(userEntity.getUserRole().equals("admin")){
            return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        } else {
            return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        }
    }

    @Override
    public String getPassword
        () {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public String getToken(){
        return userEntity.getToken();
    }

}
