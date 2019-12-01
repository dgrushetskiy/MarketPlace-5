package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IUserRoleDao;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.model.user.UserRole;
import com.senla.kedaleanid.model.user.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Repository
public class UserRoleDao extends AbstractDao<UserRole, Integer> implements IUserRoleDao {

    public boolean grantRole(Integer userId, UserRole role) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<User> update = criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> userRoot = update.from(User.class);
        update.set(User_.role, role);
        update.where(criteriaBuilder.equal(userRoot.get(User_.id), userId));

        entityManager.createQuery(update).executeUpdate();
        return true;
    }

}
