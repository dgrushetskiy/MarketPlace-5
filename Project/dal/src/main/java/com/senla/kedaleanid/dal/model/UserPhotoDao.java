package com.senla.kedaleanid.dal.model;

import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IUserPhotoDao;
import com.senla.kedaleanid.model.user.UserPhoto;
import org.springframework.stereotype.Repository;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Repository
public class UserPhotoDao extends AbstractDao<UserPhoto, Integer> implements IUserPhotoDao {

}
