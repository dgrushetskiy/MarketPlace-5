package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.model.user.UserCreds;
import com.senla.kedaleanid.serviceapi.IGenericService;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IUserCredsService extends IGenericService<UserCreds, Integer> {

    IModelDto tryAuthorisation(String login, String password, Class convertToClazz);

    IModelDto getCredsByLogin(String login, Class convertToClazz);

}
