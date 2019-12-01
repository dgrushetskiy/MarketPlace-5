package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IChatDao;
import com.senla.kedaleanid.model.chat.Chat;
import com.senla.kedaleanid.model.chat.Chat_;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.model.user.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@SuppressWarnings("unchecked")

@Repository
public class ChatDao extends AbstractDao<Chat, Integer> implements IChatDao {

    public List<Chat> getChatUsingUsers(int senderId, int recipientId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Chat.class);

        Root<Chat> chatRoot = criteriaQuery.from(Chat.class);
        Join<Chat, User> userJoin = chatRoot.join(Chat_.userList);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(userJoin.get(User_.id), senderId));
        predicates.add(criteriaBuilder.equal(userJoin.get(User_.id), recipientId));

        return entityManager.createQuery(criteriaQuery
                .select(chatRoot)
                .where(criteriaBuilder
                        .or(predicates.toArray(new Predicate[predicates.size()])))
                .groupBy(chatRoot.get(Chat_.chatId))
                .having(criteriaBuilder
                        .gt(criteriaBuilder.count(chatRoot), 1)))
                .getResultList();

    }

}


