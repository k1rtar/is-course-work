package com.algoforge.authservice.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

public class AlgoUserDetails implements UserDetails {

    @Getter
    private AlgoUser algoUser;
    private Collection<? extends GrantedAuthority> authorities;

    public AlgoUserDetails(AlgoUser algoUser, Collection<? extends GrantedAuthority> authorities) {
        this.algoUser = algoUser;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return algoUser.getPassword();
    }

    @Override
    public String getUsername() {
        return algoUser.getUsername();
    }

}