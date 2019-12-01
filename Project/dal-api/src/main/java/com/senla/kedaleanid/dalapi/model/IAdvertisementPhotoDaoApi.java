package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.advertisement.AdvertisementPhoto;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IAdvertisementPhotoDaoApi extends IGenericDao<AdvertisementPhoto, Integer> {

    void deletePhotos(Integer adId);
}
