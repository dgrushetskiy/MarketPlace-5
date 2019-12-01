package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.dto.transaction.AdvTransactionDto;
import com.senla.kedaleanid.model.advertisement.AdvertisementPaidUntil;
import com.senla.kedaleanid.serviceapi.IGenericService;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IAdvertisementPaidUntilService extends IGenericService<AdvertisementPaidUntil, Integer> {

    boolean payAd(AdvTransactionDto advTransactionDto);

}
