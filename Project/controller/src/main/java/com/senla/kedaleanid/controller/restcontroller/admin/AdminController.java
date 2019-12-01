package com.senla.kedaleanid.controller.restcontroller.admin;

import com.senla.kedaleanid.controller.restcontroller.AbstractController;
import com.senla.kedaleanid.model.advertisement.AdvertisementState;
import com.senla.kedaleanid.model.user.UserRole;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementService;
import com.senla.kedaleanid.serviceapi.model.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;

/**
 * Created by earthofmarble on Sep, 2019
 */
@RestController
@RequestMapping(path = "admin", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class AdminController extends AbstractController {

    private IUserRoleService userRoleService;
    private IAdvertisementService advertisementService;

    @Autowired
    public AdminController(IUserRoleService userRoleService, IAdvertisementService advertisementService) {
        this.userRoleService = userRoleService;
        this.advertisementService = advertisementService;
    }

    @PutMapping(value = "/grant-role")
    public boolean grantRole(@RequestParam(value = "userId") @Positive int userId,
                             @RequestParam(value = "role") UserRole userRole,
                             HttpServletResponse response) {

        if (userRoleService.grantRole(userId, userRole)) {
            sendRedirect(response, "/users/" + userId);
        }
        return false;
    }

    @PutMapping(value = "/change-state")
    public boolean grantRole(@RequestParam(value = "adId") @Positive int adId,
                             @RequestParam(value = "adState") AdvertisementState adState,
                             HttpServletResponse response) {
        if (advertisementService.changeState(adId, adState)) {
            sendRedirect(response, "/ads/" + adId);
        }
        return false;
    }

}
