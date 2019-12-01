package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.ISecretCodeDao;
import com.senla.kedaleanid.model.user.SecretCode;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.ISecretCodeService;
import com.senla.kedaleanid.utility.exception.NullDtoObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class SecretCodeService extends AbstractService<SecretCode, Integer>
        implements ISecretCodeService {

    private ISecretCodeDao secretCodeDao;

    @Autowired
    public SecretCodeService(ISecretCodeDao daoObject) {
        this.secretCodeDao = daoObject;
        super.dataAccessObject = this.secretCodeDao;
    }

    public ISecretCodeDao getSecretCodeDao() {
        return secretCodeDao;
    }

    @Transactional
    public boolean tryCode(Integer userId, Integer code) {
        if (userId != null && code != null) {
            List<SecretCode> codes = secretCodeDao.tryCode(userId, code);
            if (codes != null && !codes.isEmpty()) {
                secretCodeDao.deleteUserCodes(userId);
                return true;
            }
            return false;
        } else {
            throw new NullDtoObjectException("Null parameters received: userId:" + userId + ", code: " + code);
        }
    }

}
