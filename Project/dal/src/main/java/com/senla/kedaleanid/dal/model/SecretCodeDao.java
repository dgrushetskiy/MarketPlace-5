package com.senla.kedaleanid.dal.model;

import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.ISecretCodeDao;
import com.senla.kedaleanid.model.user.SecretCode;
import com.senla.kedaleanid.model.user.SecretCode_;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.model.user.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Repository
public class SecretCodeDao extends AbstractDao<SecretCode, Integer> implements ISecretCodeDao {

    public List<SecretCode> tryCode(Integer userId, Integer code) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SecretCode> criteriaQuery = criteriaBuilder.createQuery(SecretCode.class);

        Root<SecretCode> codeRoot = criteriaQuery.from(SecretCode.class);
        Join<SecretCode, User> userJoin = codeRoot.join(SecretCode_.user);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(codeRoot.get(SecretCode_.code), code));
        predicates.add(criteriaBuilder.equal(userJoin.get(User_.id), userId));

        return entityManager.createQuery(criteriaQuery
                .select(codeRoot)
                .where(criteriaBuilder
                        .and(predicates.toArray(new Predicate[predicates.size()]))))
                .getResultList();
    }

    public void deleteUserCodes(Integer userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<SecretCode> criteriaDelete = criteriaBuilder.createCriteriaDelete(SecretCode.class);
        Root<SecretCode> codeRoot = criteriaDelete.from(SecretCode.class);
        criteriaDelete.where(criteriaBuilder.equal(codeRoot.get(SecretCode_.user).get(User_.id), userId));

        entityManager.createQuery(criteriaDelete).executeUpdate();
    }

}
