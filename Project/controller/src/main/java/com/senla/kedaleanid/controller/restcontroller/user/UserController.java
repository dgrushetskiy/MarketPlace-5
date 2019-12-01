package com.senla.kedaleanid.controller.restcontroller.user;

import com.senla.kedaleanid.controller.restcontroller.AbstractController;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.advertisement.advertisement.AdvertisementDto;
import com.senla.kedaleanid.dto.userdto.user.UserDto;
import com.senla.kedaleanid.dto.userdto.user.UserExtendedDto;
import com.senla.kedaleanid.dto.userdto.user.UserInfoDto;
import com.senla.kedaleanid.dto.userdto.user.UserProfileDto;
import com.senla.kedaleanid.model.advertisement.AdvertisementState;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementService;
import com.senla.kedaleanid.serviceapi.model.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@RestController
@RequestMapping(path = "users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController extends AbstractController {

    private IUserService userService;
    private IAdvertisementService adService;

    @Autowired
    public UserController(IUserService userService, IAdvertisementService adService) {
        this.userService = userService;
        this.adService = adService;
    }

    private void checkIdMatch(Integer urlId, Integer objectId) {
        if (!urlId.equals(objectId)) {
            throw new AccessDeniedException("Access Denied. Given id: [" + urlId + "] doesn't match required [" + objectId + "]");
        }
    }

    @GetMapping("/")
    public List<UserDto> getAllWithPagination(@RequestParam(value = "fn", required = false) @Size(min = 1, max = 20) String userFirstName,
                                              @RequestParam(value = "ln", required = false) @Size(min = 1, max = 20) String userLastName,
                                              @RequestParam(value = "first", required = false) @PositiveOrZero Integer firstElement,
                                              @RequestParam(value = "size", required = false) @PositiveOrZero Integer pageSize) {
        Pagination pagination = configurePagination(firstElement, pageSize);
        List<IModelDto> list = userService.getUsers(userFirstName, userLastName, pagination.getFirstElement(),
                pagination.getPageSize(), UserDto.class);
        return castModelDtoList(list, UserDto.class);
    }

    @GetMapping(value = "/{id}")
    public UserExtendedDto getById(@PathVariable(value = "id") int id) {
        UserExtendedDto userDto = (UserExtendedDto) userService.readByPrimaryKey(id, UserExtendedDto.class);
        return userDto;
    }


    @GetMapping(value = "/{id}/closed-ads")
    public List<AdvertisementDto> getClosedAds(@PathVariable(value = "id") @Positive int id,
                                               @RequestParam(value = "first", required = false) @PositiveOrZero Integer firstElement,
                                               @RequestParam(value = "size", required = false) @PositiveOrZero Integer pageSize) {
        Pagination pagination = configurePagination(firstElement, pageSize);
        return adService.getAdsByState(id, pagination.getFirstElement(), pagination.getPageSize(), AdvertisementState.CLOSED);
    }

    @GetMapping(value = "/{id}/rejected-ads")
    public List<AdvertisementDto> getRejectedAds(@PathVariable(value = "id") @Positive int id,
                                                 @RequestParam(value = "first", required = false) @PositiveOrZero Integer firstElement,
                                                 @RequestParam(value = "size", required = false) @PositiveOrZero Integer pageSize) {
        Pagination pagination = configurePagination(firstElement, pageSize);
        return adService.getAdsByState(id, pagination.getFirstElement(), pagination.getPageSize(), AdvertisementState.REJECTED);
    }

    @GetMapping(value = "/{id}/moderating-ads")
    public List<AdvertisementDto> getModeratingAds(@PathVariable(value = "id") @Positive int id,
                                                   @RequestParam(value = "first", required = false) @PositiveOrZero Integer firstElement,
                                                   @RequestParam(value = "size", required = false) @PositiveOrZero Integer pageSize) {
        Pagination pagination = configurePagination(firstElement, pageSize);
        return adService.getAdsByState(id, pagination.getFirstElement(), pagination.getPageSize(), AdvertisementState.MODERATING);
    }

    @PutMapping(value = "/{id}/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean update(@PathVariable(value = "id") @Positive int id,
                          @RequestBody @Validated(UserProfileDto.UpdateUserGroup.class) UserProfileDto object) {
        checkIdMatch(id, object.getId());
        return userService.update(object);
    }

    @DeleteMapping(value = "/{id}/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean delete(@PathVariable(value = "id") @Positive int id,
                          @RequestBody @Validated(UserInfoDto.DeleteUserGroup.class) UserInfoDto object) {
        checkIdMatch(id, object.getId());
        return userService.delete(object);
    }
}
