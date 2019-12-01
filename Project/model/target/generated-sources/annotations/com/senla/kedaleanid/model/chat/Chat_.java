package com.senla.kedaleanid.model.chat;

import com.senla.kedaleanid.model.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Chat.class)
public abstract class Chat_ {

	public static volatile SetAttribute<Chat, User> userList;
	public static volatile SingularAttribute<Chat, Integer> chatId;
	public static volatile SingularAttribute<Chat, String> chatTopic;
	public static volatile SetAttribute<Chat, Message> messages;

	public static final String USER_LIST = "userList";
	public static final String CHAT_ID = "chatId";
	public static final String CHAT_TOPIC = "chatTopic";
	public static final String MESSAGES = "messages";

}

