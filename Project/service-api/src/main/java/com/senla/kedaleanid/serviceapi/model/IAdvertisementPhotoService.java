package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.model.advertisement.AdvertisementPhoto;
import com.senla.kedaleanid.serviceapi.IGenericService;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IAdvertisementPhotoService extends IGenericService<AdvertisementPhoto, Integer> {

    void deletePhotos(Integer adId);
}
