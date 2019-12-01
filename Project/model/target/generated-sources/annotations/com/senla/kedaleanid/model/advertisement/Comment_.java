package com.senla.kedaleanid.model.advertisement;

import com.senla.kedaleanid.model.user.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ {

	public static volatile SingularAttribute<Comment, User> owner;
	public static volatile SingularAttribute<Comment, Timestamp> date;
	public static volatile SingularAttribute<Comment, Advertisement> advertisement;
	public static volatile SingularAttribute<Comment, Integer> commentId;
	public static volatile SingularAttribute<Comment, String> commentText;

	public static final String OWNER = "owner";
	public static final String DATE = "date";
	public static final String ADVERTISEMENT = "advertisement";
	public static final String COMMENT_ID = "commentId";
	public static final String COMMENT_TEXT = "commentText";

}

