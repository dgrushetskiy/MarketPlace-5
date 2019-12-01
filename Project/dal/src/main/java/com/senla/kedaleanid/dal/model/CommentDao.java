package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.ICommentDao;
import com.senla.kedaleanid.model.advertisement.Advertisement_;
import com.senla.kedaleanid.model.advertisement.Comment;
import com.senla.kedaleanid.model.advertisement.Comment_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Repository
public class CommentDao extends AbstractDao<Comment, Integer> implements ICommentDao {

    public List<Comment> getAdComments(int adId, int firstElement, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> commentRoot = criteriaQuery.from(Comment.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(commentRoot.get(Comment_.advertisement).get(Advertisement_.adId), adId));

        EntityGraph entityGraph = entityManager.getEntityGraph("commentGraph");
        return entityManager.createQuery(criteriaQuery
                .orderBy(criteriaBuilder.desc(commentRoot.get(Comment_.date)))
                .where(criteriaBuilder
                        .and(predicates.toArray(new Predicate[predicates.size()]))))
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .setFirstResult(firstElement)
                .setMaxResults(pageSize)
                .getResultList();
    }

}
