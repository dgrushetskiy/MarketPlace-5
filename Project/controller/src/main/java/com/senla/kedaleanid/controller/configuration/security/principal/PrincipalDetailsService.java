package com.senla.kedaleanid.controller.configuration.security.principal;

import com.senla.kedaleanid.dto.userdto.user.UserProfileDto;
import com.senla.kedaleanid.serviceapi.model.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Component
public class PrincipalDetailsService implements UserDetailsService {

    private IUserService userService;

    @Autowired
    public PrincipalDetailsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfileDto userProfileDto = userService.getUserByLogin(username);
        if (userProfileDto.getUserCreds() == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(userProfileDto);
    }

}
