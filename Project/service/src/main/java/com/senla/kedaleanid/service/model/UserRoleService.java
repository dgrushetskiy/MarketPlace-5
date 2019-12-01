package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IUserDao;
import com.senla.kedaleanid.dalapi.model.IUserRoleDao;
import com.senla.kedaleanid.model.user.UserRole;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IUserRoleService;
import com.senla.kedaleanid.utility.exception.NoDbRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class UserRoleService extends AbstractService<UserRole, Integer> implements IUserRoleService {

    private IUserRoleDao userRoleDao;
    private IUserDao userDao;

    @Autowired
    public UserRoleService(IUserRoleDao userRoleDao, IUserDao userDao) {
        this.userRoleDao = userRoleDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public boolean grantRole(Integer userId, UserRole role) {
        if (userDao.readById(userId) == null) {
            throw new NoDbRecordException("There is no user with id [" + userId + "]");
        }
        return userRoleDao.grantRole(userId, role);
    }
}
