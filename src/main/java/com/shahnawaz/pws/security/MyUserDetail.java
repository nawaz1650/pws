package com.shahnawaz.pws.security;

import com.shahnawaz.pws.entities.PwsUser;
import com.shahnawaz.pws.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyUserDetail implements UserDetails {
    PwsUser user;

    public MyUserDetail(PwsUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles=user.getRoles();
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for (Role r:
roles             ) {
               authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getOtp();
    }

    @Override
    public String getUsername() {
        return user.getMobile();
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
}
