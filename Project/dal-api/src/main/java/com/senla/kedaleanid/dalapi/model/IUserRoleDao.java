package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.user.UserRole;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IUserRoleDao extends IGenericDao<UserRole, Integer> {

    boolean grantRole(Integer userId, UserRole role);

}
