package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IUserCredsDao;
import com.senla.kedaleanid.model.user.UserCreds;
import com.senla.kedaleanid.model.user.UserCreds_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@SuppressWarnings("unchecked")
@Repository
public class UserCredsDao extends AbstractDao<UserCreds, Integer> implements IUserCredsDao {

    private static Logger logger = LoggerFactory.getLogger(UserCredsDao.class);

    private UserCreds getByPredicates(List<Predicate> predicates, CriteriaQuery criteriaQuery) {
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        List<UserCreds> credsList = entityManager.createQuery(criteriaQuery).getResultList();
        if (credsList.size() > 1) {
            logger.info("More than 1 element returned");
            throw new RuntimeException();
        } else if (credsList.size() == 1) {
            return credsList.get(0);
        } else {
            return null;
        }
    }


    @Override
    public UserCreds tryAuthorisation(String login, String password) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(UserCreds.class);
        Root<UserCreds> rootEntry = criteriaQuery.from(UserCreds.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(rootEntry.get(UserCreds_.login), login));
        predicates.add(criteriaBuilder.equal(rootEntry.get(UserCreds_.password), password));
        return getByPredicates(predicates, criteriaQuery);
    }


    @Override
    public UserCreds checkLoginPresent(String login) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(UserCreds.class);
        Root<UserCreds> rootEntry = criteriaQuery.from(UserCreds.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(rootEntry.get(UserCreds_.login), login));
        return getByPredicates(predicates, criteriaQuery);
    }

    @Override
    public UserCreds getCredsByLogin(String login) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(UserCreds.class);

        Root<UserCreds> root = criteriaQuery.from(UserCreds.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get(UserCreds_.login), login));
        TypedQuery<UserCreds> query = entityManager.createQuery(criteriaQuery);
        List<UserCreds> results = query.getResultList();
        if (results.size() > 1) {
            logger.info("More than 1 element returned");
            throw new RuntimeException();
        } else if (results.size() == 1) {
            return results.get(0);
        } else {
            logger.info("No creds with such login");
            return null;
        }

    }
}
