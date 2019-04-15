package com.project.booking.security.details;

import com.project.booking.entity.Employeer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EmployeerDetailsImpl implements UserDetails {

    private Employeer employeer;

    public EmployeerDetailsImpl(Employeer employeer) {
        this.employeer = employeer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = employeer.getRole().name();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return employeer.getPassword();
    }

    @Override
    public String getUsername() {
        return employeer.getName();
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
        return employeer.isActive();
    }
}
