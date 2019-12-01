package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IAdvertisementPhotoDaoApi;
import com.senla.kedaleanid.model.advertisement.AdvertisementPhoto;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class AdvertisementPhotoService extends AbstractService<AdvertisementPhoto, Integer>
        implements IAdvertisementPhotoService {

    private IAdvertisementPhotoDaoApi advertisementPhotoDao;

    @Autowired
    public AdvertisementPhotoService(IAdvertisementPhotoDaoApi daoObject) {
        this.advertisementPhotoDao = daoObject;
        super.dataAccessObject = this.advertisementPhotoDao;
    }

    public IAdvertisementPhotoDaoApi getAdvertisementPhotoDao() {
        return advertisementPhotoDao;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deletePhotos(Integer adId) {
        advertisementPhotoDao.deletePhotos(adId);
    }

}
