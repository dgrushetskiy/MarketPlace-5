package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.userdto.user.UserProfileDto;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.serviceapi.IGenericService;

import java.util.List;


/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IUserService extends IGenericService<User, Integer> {

    List<IModelDto> getUsers(String userFirstName, String userLastName, int firstElement, int pageSize, Class convertToClazz);

    UserProfileDto getUserByLogin(String login);

}
