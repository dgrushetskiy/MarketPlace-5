package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IAdvertisementCategoryDao;
import com.senla.kedaleanid.model.advertisement.AdvertisementCategory;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by earthofmarble on Sep, 2019
 */

@Service
public class AdvertisementCategoryService extends AbstractService<AdvertisementCategory, Integer>
        implements IAdvertisementCategoryService {

    private IAdvertisementCategoryDao advertisementCategoryDao;

    @Autowired
    public AdvertisementCategoryService(IAdvertisementCategoryDao daoObject) {
        this.advertisementCategoryDao = daoObject;
        super.dataAccessObject = this.advertisementCategoryDao;
    }

    public IAdvertisementCategoryDao getAdvertisementCategoryDao() {
        return advertisementCategoryDao;
    }
}
