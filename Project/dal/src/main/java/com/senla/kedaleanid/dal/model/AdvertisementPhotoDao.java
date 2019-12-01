package com.senla.kedaleanid.dal.model;

import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IAdvertisementPhotoDaoApi;
import com.senla.kedaleanid.model.advertisement.AdvertisementPhoto;
import com.senla.kedaleanid.model.advertisement.AdvertisementPhoto_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Repository
public class AdvertisementPhotoDao extends AbstractDao<AdvertisementPhoto, Integer> implements IAdvertisementPhotoDaoApi {

    public void deletePhotos(Integer adId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<AdvertisementPhoto> query = criteriaBuilder.createCriteriaDelete(AdvertisementPhoto.class);
        Root<AdvertisementPhoto> root = query.from(AdvertisementPhoto.class);
        query.where(criteriaBuilder.equal(root.get(AdvertisementPhoto_.adId), adId));

        entityManager.createQuery(query).executeUpdate();
    }

}
