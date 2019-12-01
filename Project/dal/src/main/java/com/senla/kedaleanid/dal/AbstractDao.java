package com.senla.kedaleanid.dal;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.utility.exception.NoDbRecordException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by earthofmarble on Sep, 2019
 */
@SuppressWarnings("unchecked")
@Repository
public abstract class AbstractDao<T, PK extends Serializable> implements IGenericDao<T, PK> {

    @PersistenceContext(unitName = "persistence", type = PersistenceContextType.EXTENDED)
    protected EntityManager entityManager;

    public AbstractDao() {
    }

    private Class<T> getEntityType() {
        return ((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    //String fetchType: use fetchgraph/loadgraph // fetch by default
    protected Map<String, Object> createGraph(String graphName, String fetchType) {
        if (fetchType == null || (!fetchType.equals("fetchgraph") && !fetchType.equals("loadgraph")) || fetchType.isEmpty()) {
            fetchType = "fetchgraph";
        }
        EntityGraph entityGraph = entityManager.getEntityGraph(graphName);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence." + fetchType, entityGraph);
        return properties;
    }

    public void save(T object) {
        entityManager.persist(object);
    }

    public void update(T object) {
        entityManager.merge(object);
    }

    public void delete(T object) {
        entityManager.remove(object);
    }

    public T readById(PK primaryKey) {
        Class clazz = getEntityType();
        try {
            return (T) entityManager.find(clazz, primaryKey);
        } catch (NoResultException e) {
            throw new NoDbRecordException("There is no ["+clazz+"] with id [" + primaryKey + "]");
        }
    }

    public List<T> readAllWithPagination(int firstElement, int pageSize) {
        Class<T> entityType = getEntityType();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(getEntityType());
        criteriaQuery.from(entityType);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstElement)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
