package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.model.user.SecretCode;
import com.senla.kedaleanid.serviceapi.IGenericService;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface ISecretCodeService extends IGenericService<SecretCode, Integer> {

    boolean tryCode(Integer userId, Integer Code);

}

