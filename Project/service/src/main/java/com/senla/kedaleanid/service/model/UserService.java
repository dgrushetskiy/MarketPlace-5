package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IUserCredsDao;
import com.senla.kedaleanid.dalapi.model.IUserDao;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.userdto.user.UserProfileDto;
import com.senla.kedaleanid.dto.userdto.userSecondary.UserCredsDto;
import com.senla.kedaleanid.dto.userdto.userSecondary.UserPhotoDto;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.model.user.UserCreds;
import com.senla.kedaleanid.model.user.UserRole;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IUserService;
import com.senla.kedaleanid.utility.exception.NoDbRecordException;
import com.senla.kedaleanid.utility.exception.NullDtoObjectException;
import com.senla.kedaleanid.utility.exception.RecordAlreadyExistsException;
import com.senla.kedaleanid.utility.exception.TransactionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class UserService extends AbstractService<User, Integer> implements IUserService {

    private IUserDao userDao;
    private IUserCredsDao credsDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserDao daoObject, IUserCredsDao credsDaoObject, @Lazy PasswordEncoder passwordEncoder) {
        this.userDao = daoObject;
        this.credsDao = credsDaoObject;
        this.passwordEncoder = passwordEncoder;
        super.dataAccessObject = this.userDao;
    }

    private boolean checkIfLoginAvailable(UserProfileDto userDto) {
        UserCreds userCreds = credsDao.checkLoginPresent(userDto.getUserCreds().getLogin());
        return userCreds == null;
    }

    private void configurePhotoDto(UserProfileDto userDto, User user) {
        UserPhotoDto userPhotoDto = userDto.getPhotoUrl();
        if (userPhotoDto != null && userPhotoDto.getPhotoUrl() != null) {
            userPhotoDto.setId(user.getId());
        }
    }

    private void saveUser(UserCreds userCreds, UserProfileDto userDto) {
        User user = new User();
        user.setId(userCreds.getUserId());

        configurePhotoDto(userDto, user);

        user = (User) mapper.convert(userDto, User.class, user);
        user.setUserCreds(userCreds);
        user.setRole(UserRole.ROLE_USER);
        userDao.save(user);
    }

    @Transactional
    public UserProfileDto getUserByLogin(String login) {
        User user = userDao.getUserByLogin(login);
        if (user != null) {
            return (UserProfileDto) mapper.convert(userDao.getUserByLogin(login), UserProfileDto.class, null);
        } else {
            return new UserProfileDto();
        }
    }

    @Transactional
    public List<IModelDto> getUsers(String userFirstName, String userLastName,
                                    int firstElement, int pageSize, Class convertToClazz) {
        List<IModelDto> userDtos = convertListToDto(userDao.getUsers(userFirstName, userLastName, firstElement, pageSize),
                convertToClazz);
        addOneIfEmpty(userDtos, User.class);
        return userDtos;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean create(IModelDto object) {
        UserProfileDto userDto = (UserProfileDto) object;
        if (userDto.getUserCreds() == null) {
            return false;
        }
        if (!checkIfLoginAvailable(userDto)) {
            throw new RecordAlreadyExistsException("Username [" + userDto.getUserCreds().getLogin() + "] already exists!");
        }
        try {
            UserCreds userCreds = new UserCreds(userDto.getUserCreds().getLogin(),
                    passwordEncoder.encode(userDto.getUserCreds().getPassword()));
            credsDao.save(userCreds);
            saveUser(userCreds, userDto);
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction failed! \n" + e.getMessage());
        }
        return true;
    }

    @Override
    @Transactional
    public boolean update(IModelDto object) {
        UserProfileDto userDto = (UserProfileDto) object;
        tryNullDtoId(userDto);
        try {
            UserCredsDto credsDto = userDto.getUserCreds();
            if (credsDto.getPassword() != null) {
                credsDto.setPassword(passwordEncoder.encode(credsDto.getPassword()));
            }
            User model = userDao.readByIdNGraph(userDto.getId(), "userProfileGraph");
            tryNullDbRecord(model, "There is no " + User.class.getSimpleName() + " with id: " + object.getId());
            if (userDto.getPhotoUrl() != null) {
                userDto.getPhotoUrl().setId(userDto.getId());
            }
            model = (User) mapper.convert(userDto, User.class, model);
            userDao.update(model);
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction failed! \n" + e.getMessage());
        }
        return true;
    }

}
