package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IAdvertisementTypeDao;
import com.senla.kedaleanid.model.advertisement.AdvertisementType;
import org.springframework.stereotype.Repository;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Repository
public class AdvertisementTypeDao extends AbstractDao<AdvertisementType, Integer> implements IAdvertisementTypeDao {

}
