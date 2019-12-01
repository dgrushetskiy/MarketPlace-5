package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IUserDao;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.model.user.UserCreds;
import com.senla.kedaleanid.model.user.UserCreds_;
import com.senla.kedaleanid.model.user.User_;
import com.senla.kedaleanid.utility.defaultgraph.DefaultGraph;
import com.senla.kedaleanid.utility.exception.NoDbRecordException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by earthofmarble on Sep, 2019
 */
@SuppressWarnings("unchecked")
@Repository
public class UserDao extends AbstractDao<User, Integer> implements IUserDao {

    public List<User> getUsers(String userFirstName, String userLastName, int firstElement, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);

        EntityGraph entityGraph = entityManager.getEntityGraph("userGraph");

        List<Predicate> predicates = new ArrayList<>();
        if (userFirstName != null && !userFirstName.isEmpty()) {
            predicates.add(criteriaBuilder.like(userRoot.get(User_.firstName), "%" + userFirstName + "%"));
        }
        if (userLastName != null && !userLastName.isEmpty()) {
            predicates.add(criteriaBuilder.like(userRoot.get(User_.lastName), "%" + userLastName + "%"));
        }

        return entityManager.createQuery(criteriaQuery
                .select(userRoot)
                .where(criteriaBuilder
                        .and(predicates.toArray(new Predicate[predicates.size()])))
                .orderBy(criteriaBuilder
                        .desc(userRoot.get("rating"))))
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .setFirstResult(firstElement)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public User readByIdNGraph(Integer primaryKey, String graphName) {
        if (graphName == null || graphName.isEmpty()) {
            graphName = "userExtendedGraph";
        }
        System.err.println(graphName);
        Map<String, Object> hints = createGraph(graphName, "fetchgraph");
        return entityManager.find(User.class, primaryKey, hints);
    }

    @Override
    public User readById(Integer primaryKey) {
        DefaultGraph defaultGraphAnn = User.class.getAnnotation(DefaultGraph.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        if (defaultGraphAnn != null) {
            EntityGraph entityGraph = entityManager.getEntityGraph(defaultGraphAnn.name());
            try {
                return entityManager.createQuery(criteriaQuery
                        .where(criteriaBuilder.equal(userRoot.get(User_.ID), primaryKey)))
                        .setHint("javax.persistence.fetchgraph", entityGraph)
                        .getSingleResult();
            } catch (NoResultException e) {
                throw new NoDbRecordException("There is no user with id [" + primaryKey + "]");
            }
        }
        try {
            return entityManager.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(userRoot.get(User_.ID), primaryKey)))
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NoDbRecordException("There is no user with id [" + primaryKey + "]");
        }
    }

    @Override
    public User getUserByLogin(String login) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Join<User, UserCreds> credsJoin = userRoot.join(User_.userCreds);

        EntityGraph entityGraph = entityManager.getEntityGraph("userProfileGraph");
        try {
            return entityManager.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(credsJoin.get(UserCreds_.login), login)))
                    .setHint("javax.persistence.fetchgraph", entityGraph)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NoDbRecordException("There is no user with id [" + login + "]");
        }
    }


    @Override
    public void delete(User object) {
        entityManager.remove(object.getUserCreds());
    }

}
