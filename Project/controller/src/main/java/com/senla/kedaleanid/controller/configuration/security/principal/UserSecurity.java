package com.senla.kedaleanid.controller.configuration.security.principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    public boolean hasUserId(Authentication authentication, Integer userId) {
        if (!(authentication.getPrincipal() instanceof UserPrincipal)) {
            return false;
        }
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        return user.getId().equals(userId);
    }

}