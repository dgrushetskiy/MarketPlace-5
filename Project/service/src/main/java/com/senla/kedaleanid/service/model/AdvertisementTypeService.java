package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IAdvertisementTypeDao;
import com.senla.kedaleanid.model.advertisement.AdvertisementType;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class AdvertisementTypeService extends AbstractService<AdvertisementType, Integer>
        implements IAdvertisementTypeService {

    private IAdvertisementTypeDao advertisementTypeDao;

    @Autowired
    public AdvertisementTypeService(IAdvertisementTypeDao daoObject) {
        this.advertisementTypeDao = daoObject;
        super.dataAccessObject = this.advertisementTypeDao;
    }

    public IAdvertisementTypeDao getAdvertisementTypeDao() {
        return advertisementTypeDao;
    }
}
