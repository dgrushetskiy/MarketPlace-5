package com.senla.kedaleanid.dal.model;

import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IAdvertisementPaidUntilDao;
import com.senla.kedaleanid.model.advertisement.AdvertisementPaidUntil;
import com.senla.kedaleanid.model.advertisement.AdvertisementPaidUntil_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Repository
public class AdvertisementPaidUntilDao extends AbstractDao<AdvertisementPaidUntil, Integer> implements IAdvertisementPaidUntilDao {

    public void removeExpired() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaDelete<AdvertisementPaidUntil> delete = criteriaBuilder.createCriteriaDelete(AdvertisementPaidUntil.class);
        Root<AdvertisementPaidUntil> paidUntilRoot = delete.from(AdvertisementPaidUntil.class);

        delete.where(criteriaBuilder.lessThanOrEqualTo(paidUntilRoot.get(AdvertisementPaidUntil_.PAID_UNTIL),
                criteriaBuilder.currentTimestamp()));

        entityManager.createQuery(delete).executeUpdate();
    }

}
