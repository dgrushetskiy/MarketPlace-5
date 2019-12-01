package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.model.user.UserRole;
import com.senla.kedaleanid.serviceapi.IGenericService;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IUserRoleService extends IGenericService<UserRole, Integer> {

    boolean grantRole(Integer userId, UserRole role);

}
