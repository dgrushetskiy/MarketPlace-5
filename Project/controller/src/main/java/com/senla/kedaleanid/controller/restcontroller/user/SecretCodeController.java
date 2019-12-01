package com.senla.kedaleanid.controller.restcontroller.user;

import com.senla.kedaleanid.controller.restcontroller.AbstractController;
import com.senla.kedaleanid.dto.userdto.user.UserInfoDto;
import com.senla.kedaleanid.dto.userdto.user.UserProfileDto;
import com.senla.kedaleanid.dto.userdto.userSecondary.SecretCodeDto;
import com.senla.kedaleanid.serviceapi.model.ISecretCodeService;
import com.senla.kedaleanid.serviceapi.model.IUserService;
import com.senla.kedaleanid.utility.mail.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Created by earthofmarble on Oct, 2019
 */
@RestController
@RequestMapping(path = "restore-password")
public class SecretCodeController extends AbstractController {

    private final String EMAIL = "reswizcompany@gmail.com";
    private final String SUBJECT = "SECRET CODE";

    private IMailService mailService;
    private IUserService userService;
    private ISecretCodeService secretCodeService;

    @Autowired
    public SecretCodeController(IMailService mailService, IUserService userService, ISecretCodeService secretCodeService) {
        this.mailService = mailService;
        this.userService = userService;
        this.secretCodeService = secretCodeService;
    }

    private void createNSendCode(Integer receiverId, int code, String receiverEmail) {
        SecretCodeDto secretCodeDto = new SecretCodeDto();
        secretCodeDto.setUserId(receiverId);
        secretCodeDto.setCode(code);
        secretCodeService.create(secretCodeDto);

        mailService.sendEmail(EMAIL, receiverEmail, SUBJECT, "id: " + receiverId + ", your code: " + code);
    }

    @GetMapping(value = "/send-code", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean sendCode(HttpServletRequest request) {
        Integer receiverId = (Integer) request.getSession().getAttribute("userId");
        String receiverEmail = (String) request.getSession().getAttribute("email");
        int code = (int) (10000 + Math.random() * 99999); // todo ;D

        createNSendCode(receiverId, code, receiverEmail);
        return true;
    }

    @GetMapping("/get-user")
    public UserProfileDto getUser(@RequestParam(value = "login") @Size(min = 1, max = 20) String login,
                                  HttpServletRequest request, HttpServletResponse response) {
        UserProfileDto user = userService.getUserByLogin(login);
        if (user.getEmail() != null && user.getId() != null && !user.getEmail().isEmpty()) {
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("email", user.getEmail());
            sendRedirect(response, "/restore-password/send-code");
        }

        return user;
    }

    @GetMapping("/try-code")
    public UserInfoDto tryCode(@RequestParam(value = "code") @Positive Integer code,
                               @RequestParam(value = "id") @Positive Integer userId, HttpServletResponse response) {
        if (secretCodeService.tryCode(userId, code)) {
            sendRedirect(response, "/users/" + userId);
        }
        return new UserInfoDto();
    }

}
