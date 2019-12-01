package com.senla.kedaleanid.model.chat;

import com.senla.kedaleanid.model.user.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, User> owner;
	public static volatile SingularAttribute<Message, Timestamp> date;
	public static volatile SingularAttribute<Message, Chat> chat;
	public static volatile SingularAttribute<Message, Integer> messageId;
	public static volatile SingularAttribute<Message, String> text;

	public static final String OWNER = "owner";
	public static final String DATE = "date";
	public static final String CHAT = "chat";
	public static final String MESSAGE_ID = "messageId";
	public static final String TEXT = "text";

}

