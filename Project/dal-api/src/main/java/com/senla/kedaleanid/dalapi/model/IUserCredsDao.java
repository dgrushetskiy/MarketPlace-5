package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.user.UserCreds;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IUserCredsDao extends IGenericDao<UserCreds, Integer> {

    UserCreds tryAuthorisation(String login, String password);

    UserCreds getCredsByLogin(String login);

    UserCreds checkLoginPresent(String login);
}
