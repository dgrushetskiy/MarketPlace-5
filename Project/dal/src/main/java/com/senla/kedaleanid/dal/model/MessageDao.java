package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.IMessageDao;
import com.senla.kedaleanid.model.chat.Chat;
import com.senla.kedaleanid.model.chat.Chat_;
import com.senla.kedaleanid.model.chat.Message;
import com.senla.kedaleanid.model.chat.Message_;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.model.user.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@SuppressWarnings("unchecked")

@Repository
public class MessageDao extends AbstractDao<Message, Integer> implements IMessageDao {

    public List<Message> getChatMessages(int chatId, int firstElement, int pageSize, String messageText) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> messageRoot = criteriaQuery.from(Message.class);

        EntityGraph entityGraph = entityManager.getEntityGraph("messageGraph");

        List<Predicate> predicates = new ArrayList<>();
        if (messageText != null && !messageText.isEmpty()) {
            predicates.add(criteriaBuilder.like(messageRoot.get(Message_.text), "%" + messageText + "%"));
        }
        predicates.add(criteriaBuilder.equal(messageRoot.get(Message_.chat).get(Chat_.chatId), chatId));

        return entityManager.createQuery(criteriaQuery
                .orderBy(criteriaBuilder.desc(messageRoot.get(Message_.date)))
                .where(criteriaBuilder
                        .and(predicates.toArray(new Predicate[predicates.size()]))))
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .setFirstResult(firstElement)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Message> getUserChats(int userId, String chatTopic, int firstElement, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Message.class);

        Root<Message> messageRoot = criteriaQuery.from(Message.class);
        Join<Message, Chat> chatJoin = messageRoot.join(Message_.chat);
        Join<Chat, User> userJoin = chatJoin.join(Chat_.userList);

        EntityGraph entityGraph = entityManager.getEntityGraph("messageGraph");

        List<Predicate> predicates = new ArrayList<>();
        if (chatTopic != null && !chatTopic.isEmpty()) {
            predicates.add(criteriaBuilder.like(chatJoin.get(Chat_.chatTopic), "%" + chatTopic + "%"));
        }
        predicates.add(criteriaBuilder.equal(userJoin.get(User_.id), userId));

        Expression<Timestamp> messageDateExpression = messageRoot.get(Message_.date);

        return entityManager.createQuery(criteriaQuery
                .select(messageRoot)
                .where(criteriaBuilder
                        .greatest(messageDateExpression))
                .where(criteriaBuilder
                        .and(predicates.toArray(new Predicate[predicates.size()])))
                .groupBy(chatJoin.get(Chat_.chatId))
                .orderBy(criteriaBuilder
                        .desc(criteriaBuilder.greatest(messageDateExpression))))
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .setFirstResult(firstElement)
                .setMaxResults(pageSize)
                .getResultList();
    }

}
