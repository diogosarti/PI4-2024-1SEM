package com.osguri.ongforall.entities;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.transaction.Transactional;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
        return user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
        .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
        return true;
    }
    
}
