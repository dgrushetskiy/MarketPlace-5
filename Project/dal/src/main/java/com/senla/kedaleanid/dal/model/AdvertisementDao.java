package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IAdvertisementDao;
import com.senla.kedaleanid.model.advertisement.Advertisement;
import com.senla.kedaleanid.model.advertisement.AdvertisementCategory;
import com.senla.kedaleanid.model.advertisement.AdvertisementCategory_;
import com.senla.kedaleanid.model.advertisement.AdvertisementPaidUntil;
import com.senla.kedaleanid.model.advertisement.AdvertisementPaidUntil_;
import com.senla.kedaleanid.model.advertisement.AdvertisementState;
import com.senla.kedaleanid.model.advertisement.AdvertisementType;
import com.senla.kedaleanid.model.advertisement.AdvertisementType_;
import com.senla.kedaleanid.model.advertisement.Advertisement_;
import com.senla.kedaleanid.model.user.User_;
import com.senla.kedaleanid.utility.defaultgraph.DefaultGraph;
import com.senla.kedaleanid.utility.exception.NoDbRecordException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@SuppressWarnings("unchecked")
@Repository
public class AdvertisementDao extends AbstractDao<Advertisement, Integer> implements IAdvertisementDao {

    private void fillFilterPredicates(Root<Advertisement> advertisementRoot, List<Predicate> predicatesTemp,
                                      List<Predicate> predicates, List<AdvertisementCategory> categoryList,
                                      List<AdvertisementType> typeList, String name, Long minPrice,
                                      Long maxPrice, CriteriaBuilder criteriaBuilder) {

        fillCategoryPredicates(categoryList, predicatesTemp, predicates, criteriaBuilder, advertisementRoot);
        fillTypePredicates(typeList, predicatesTemp, predicates, criteriaBuilder, advertisementRoot);
        fillPricePredicates(minPrice, maxPrice, predicates, criteriaBuilder, advertisementRoot);
        fillNamePredicates(name, predicates, criteriaBuilder, advertisementRoot);

        predicates.add(criteriaBuilder.equal(advertisementRoot.get(Advertisement_.adState), AdvertisementState.ACTIVE));

    }

    private void fillCategoryPredicates(List<AdvertisementCategory> categoryList, List<Predicate> predicatesTemp,
                                        List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<Advertisement> advertisementRoot) {
        if (categoryList != null && !categoryList.isEmpty()) {
            for (AdvertisementCategory category : categoryList) {
                predicatesTemp.add(criteriaBuilder.equal(advertisementRoot.get(Advertisement_.adCategory)
                                .get(AdvertisementCategory_.categoryId),
                        category.getCategoryId()));
            }
            predicates.add(criteriaBuilder.or(predicatesTemp.toArray(new Predicate[predicatesTemp.size()])));
            predicatesTemp.clear();
        }
    }

    private void fillTypePredicates(List<AdvertisementType> typeList, List<Predicate> predicatesTemp,
                                    List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<Advertisement> advertisementRoot) {
        if (typeList != null && !typeList.isEmpty()) {
            for (AdvertisementType type : typeList) {
                predicatesTemp.add(criteriaBuilder.equal(advertisementRoot.get(Advertisement_.adType)
                                .get(AdvertisementType_.typeId),
                        type.getTypeId()));
            }
            predicates.add(criteriaBuilder.or(predicatesTemp.toArray(new Predicate[predicatesTemp.size()])));
            predicatesTemp.clear();
        }
    }


    private void fillPricePredicates(Long minPrice, Long maxPrice, List<Predicate> predicates,
                                     CriteriaBuilder criteriaBuilder, Root<Advertisement> advertisementRoot) {
        if (minPrice != null) {
            predicates.add(criteriaBuilder.gt(advertisementRoot.get(Advertisement_.adPrice), minPrice));
        }

        if (maxPrice != null) {
            predicates.add(criteriaBuilder.lt(advertisementRoot.get(Advertisement_.adPrice), maxPrice));
        }
    }

    private void fillNamePredicates(String name, List<Predicate> predicates,
                                    CriteriaBuilder criteriaBuilder, Root<Advertisement> advertisementRoot) {
        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(advertisementRoot.get(Advertisement_.adName), "%" + name + "%"));
        }
    }

    private List<Order> fillOrderList(CriteriaBuilder criteriaBuilder, Root<Advertisement> advertisementRoot,
                                      Join<Advertisement, AdvertisementPaidUntil> paidUntilJoin) {
        List<Order> orderList = new ArrayList();
        orderList.add(criteriaBuilder.desc(paidUntilJoin.get(AdvertisementPaidUntil_.paidUntil)));
        orderList.add(criteriaBuilder.desc(advertisementRoot.join(Advertisement_.owner).get(User_.rating)));
        return orderList;
    }

    @Override
    public Advertisement readById(Integer primaryKey) {
        DefaultGraph defaultGraphAnn = Advertisement.class.getAnnotation(DefaultGraph.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Advertisement> criteriaQuery = criteriaBuilder.createQuery(Advertisement.class);
        Root<Advertisement> advertisementRoot = criteriaQuery.from(Advertisement.class);

        if (defaultGraphAnn != null) {
            EntityGraph entityGraph = entityManager.getEntityGraph(defaultGraphAnn.name());
            try {
                return entityManager.createQuery(criteriaQuery
                        .where(criteriaBuilder.equal(advertisementRoot.get(Advertisement_.adId), primaryKey)))
                        .setHint("javax.persistence.fetchgraph", entityGraph)
                        .getSingleResult();
            } catch (NoResultException e) {
                throw new NoDbRecordException("There is no advertisement with id [" + primaryKey + "]");
            }
        }

        return entityManager.createQuery(criteriaQuery
                .where(criteriaBuilder.equal(advertisementRoot.get(Advertisement_.adId), primaryKey)))
                .getSingleResult();
    }

    @Override
    public List<Advertisement> getAdsWithFilter(String name, List<AdvertisementCategory> categoryList,
                                                List<AdvertisementType> typeList, Long minPrice,
                                                Long maxPrice, int firstElement, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Advertisement.class);
        List<Predicate> predicatesTemp = new ArrayList<>();
        List<Predicate> predicates = new ArrayList<>();

        Root<Advertisement> advertisementRoot = criteriaQuery.from(Advertisement.class);
        Join<Advertisement, AdvertisementPaidUntil> paidUntilJoin =
                advertisementRoot.join(Advertisement_.paidUntil, JoinType.LEFT);
        EntityGraph entityGraph = entityManager.getEntityGraph("advertisementGraph");

        fillFilterPredicates(advertisementRoot, predicatesTemp, predicates, categoryList,
                typeList, name, minPrice, maxPrice, criteriaBuilder);

        List<Order> orderList = fillOrderList(criteriaBuilder, advertisementRoot, paidUntilJoin);

        criteriaQuery.orderBy(orderList);

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(criteriaQuery.select(advertisementRoot))
                .setFirstResult(firstElement)
                .setMaxResults(pageSize)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }

    public List<Advertisement> getUserAdsByState(Integer ownerId, int firstElement, int pageSize, AdvertisementState state) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Advertisement.class);

        Root<Advertisement> advertisementRoot = criteriaQuery.from(Advertisement.class);

        EntityGraph entityGraph = entityManager.getEntityGraph("advertisementGraph");

        criteriaQuery.orderBy(criteriaBuilder.desc(advertisementRoot.get(Advertisement_.adDate)));

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(advertisementRoot.get(Advertisement_.adState), state));
        predicates.add(criteriaBuilder.equal(advertisementRoot.get(Advertisement_.owner).get(User_.id), ownerId));

        return entityManager.createQuery(criteriaQuery.select(advertisementRoot)
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))))
                .setFirstResult(firstElement)
                .setMaxResults(pageSize)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }

    public boolean changeState(Integer adId, AdvertisementState advertisementState) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Advertisement> update = criteriaBuilder.createCriteriaUpdate(Advertisement.class);
        Root<Advertisement> userRoot = update.from(Advertisement.class);
        update.set(Advertisement_.adState, advertisementState);
        update.where(criteriaBuilder.equal(userRoot.get(Advertisement_.adId), adId));

        entityManager.createQuery(update).executeUpdate();
        return true;
    }

}
