package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.advertisement.AdvertisementPaidUntil;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IAdvertisementPaidUntilDao extends IGenericDao<AdvertisementPaidUntil, Integer> {

    void removeExpired();

}
