package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.user.SecretCode;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface ISecretCodeDao extends IGenericDao<SecretCode, Integer> {

    List<SecretCode> tryCode(Integer userId, Integer Code);

    void deleteUserCodes(Integer userId);

}
