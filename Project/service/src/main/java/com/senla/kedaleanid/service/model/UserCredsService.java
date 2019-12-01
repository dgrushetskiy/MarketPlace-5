package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IUserCredsDao;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.model.user.UserCreds;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IUserCredsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class UserCredsService extends AbstractService<UserCreds, Integer>
        implements IUserCredsService {

    private IUserCredsDao credsDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserCredsService(IUserCredsDao daoObject, PasswordEncoder passwordEncoder) {
        this.credsDao = daoObject;
        this.passwordEncoder = passwordEncoder;
        super.dataAccessObject = this.credsDao;
    }


    public IUserCredsDao getCredsDao() {
        return credsDao;
    }

    @Override
    @Transactional
    public IModelDto tryAuthorisation(String login, String password, Class convertToClazz) {

        UserCreds userCreds = credsDao.getCredsByLogin(login);
        if (userCreds != null && passwordEncoder.matches(password, userCreds.getPassword())) {
            return convertSingleModelToDto(userCreds, convertToClazz);
        } else {
            return null;
        }
    }

    @Override
    public IModelDto getCredsByLogin(String login, Class convertToClazz) {
        return convertSingleModelToDto(credsDao.getCredsByLogin(login), convertToClazz);
    }

}
