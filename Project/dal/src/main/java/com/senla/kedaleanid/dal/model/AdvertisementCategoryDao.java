package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IAdvertisementCategoryDao;
import com.senla.kedaleanid.model.advertisement.AdvertisementCategory;
import org.springframework.stereotype.Repository;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Repository
public class AdvertisementCategoryDao extends AbstractDao<AdvertisementCategory, Integer> implements IAdvertisementCategoryDao {

}
