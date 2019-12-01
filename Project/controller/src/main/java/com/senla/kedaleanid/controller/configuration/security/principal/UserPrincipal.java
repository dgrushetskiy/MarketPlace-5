package com.senla.kedaleanid.controller.configuration.security.principal;

import com.senla.kedaleanid.dto.userdto.user.UserProfileDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by earthofmarble on Oct, 2019
 */

public class UserPrincipal implements UserDetails {

    private UserProfileDto user;

    public UserPrincipal(UserProfileDto user) {
        super();
        this.user = user;
    }

    private void checkUser() {
        if (user == null) {
            throw new AccessDeniedException("You have no authority to perform this action");
        }
    }


    public Integer getId() {
        checkUser();
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        checkUser();
        return Collections.singleton(new SimpleGrantedAuthority(String.valueOf(user.getRole())));
    }

    @Override
    public String getPassword() {
        checkUser();
        return user.getUserCreds().getPassword();
    }

    @Override
    public String getUsername() {
        checkUser();
        return user.getUserCreds().getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        checkUser();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        checkUser();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        checkUser();
        return true;
    }

    @Override
    public boolean isEnabled() {
        checkUser();
        return true;
    }
}
