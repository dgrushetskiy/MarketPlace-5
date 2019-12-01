package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.user.User;

import java.util.List;


/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IUserDao extends IGenericDao<User, Integer> {

    List<User> getUsers(String userFirstName, String userLastName, int firstElement, int pageSize);

    User readByIdNGraph(Integer primaryKey, String graphName);

    User getUserByLogin(String login);
}
