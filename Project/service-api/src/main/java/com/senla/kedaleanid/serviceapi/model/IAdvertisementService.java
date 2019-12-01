package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.dto.advertisement.advertisement.AdvertisementDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementFilterDto;
import com.senla.kedaleanid.model.advertisement.Advertisement;
import com.senla.kedaleanid.model.advertisement.AdvertisementState;
import com.senla.kedaleanid.serviceapi.IGenericService;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IAdvertisementService extends IGenericService<Advertisement, Integer> {

    List<AdvertisementDto> getAdsWithFilter(AdvertisementFilterDto adFilter, int firstElementPos, int pageSize);

    List<AdvertisementDto> getAdsByState(Integer ownerId, int firstElement, int pageSize, AdvertisementState state);

    boolean changeState(Integer adId, AdvertisementState adState);

}
