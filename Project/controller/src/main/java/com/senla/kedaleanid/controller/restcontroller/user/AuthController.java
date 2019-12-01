package com.senla.kedaleanid.controller.restcontroller.user;

import com.senla.kedaleanid.controller.restcontroller.AbstractController;
import com.senla.kedaleanid.dto.userdto.user.UserDto;
import com.senla.kedaleanid.dto.userdto.user.UserProfileDto;
import com.senla.kedaleanid.dto.userdto.userSecondary.UserCredsDto;
import com.senla.kedaleanid.serviceapi.model.IUserCredsService;
import com.senla.kedaleanid.serviceapi.model.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by earthofmarble on Oct, 2019
 */
@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

public class AuthController extends AbstractController {
    private IUserCredsService credsService;
    private IUserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(IUserCredsService credsService, IUserService userService,
                          AuthenticationManager authenticationManager) {
        this.credsService = credsService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping(value = "sign-in")
    public UserDto logIn(@RequestBody @Validated(UserCredsDto.SignInGroup.class) UserCredsDto userCreds,
                         HttpServletRequest request, HttpServletResponse response) {
        UserCredsDto userCredsDto = (UserCredsDto) credsService.tryAuthorisation(userCreds.getLogin(),
                userCreds.getPassword(),
                UserCredsDto.class);
        if (userCredsDto != null && userCredsDto.getId() != null) {
            authenticateUserAndSetSession(userCreds.getLogin(), userCreds.getPassword(), request, authenticationManager);
            sendRedirect(response, "users/" + userCredsDto.getId());
        }
        return new UserDto();
    }

    @GetMapping(value = "log-out")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            request.getSession().invalidate();
        }
        return "false";
    }

    @PostMapping("sign-up")
    public UserProfileDto signUp(@RequestBody @Validated(UserProfileDto.CreateUserGroup.class) UserProfileDto object,
                                 HttpServletRequest request, HttpServletResponse response) {
        boolean create = userService.create(object);
        if (create) {
            authenticateUserAndSetSession(object.getUserCreds().getLogin(), object.getUserCreds().getPassword(), request, authenticationManager);
        }
        sendRedirect(response, "ads/");
        return new UserProfileDto();
    }
}
