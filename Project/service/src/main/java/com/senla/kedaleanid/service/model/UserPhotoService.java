package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IUserPhotoDao;
import com.senla.kedaleanid.model.user.UserPhoto;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IUserPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class UserPhotoService extends AbstractService<UserPhoto, Integer>
        implements IUserPhotoService {

    private IUserPhotoDao userPhotoDao;

    @Autowired
    public UserPhotoService(IUserPhotoDao daoObject) {
        this.userPhotoDao = daoObject;
        super.dataAccessObject = this.userPhotoDao;
    }

    public IUserPhotoDao getUserPhotoDao() {
        return userPhotoDao;
    }

}
