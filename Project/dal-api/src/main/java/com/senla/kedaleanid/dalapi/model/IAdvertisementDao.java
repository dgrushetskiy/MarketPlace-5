package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.advertisement.Advertisement;
import com.senla.kedaleanid.model.advertisement.AdvertisementCategory;
import com.senla.kedaleanid.model.advertisement.AdvertisementState;
import com.senla.kedaleanid.model.advertisement.AdvertisementType;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IAdvertisementDao extends IGenericDao<Advertisement, Integer> {

    List<Advertisement> getAdsWithFilter(String name, List<AdvertisementCategory> categoryList,
                                         List<AdvertisementType> typeList, Long minPrice, Long maxPrice,
                                         int firstElement, int pageSize);

    List<Advertisement> getUserAdsByState(Integer ownerId, int first, int size, AdvertisementState state);

    boolean changeState(Integer adId, AdvertisementState advertisementState);

}
