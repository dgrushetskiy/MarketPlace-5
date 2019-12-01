package com.senla.kedaleanid.model.user;

import com.senla.kedaleanid.model.advertisement.Advertisement;
import com.senla.kedaleanid.model.advertisement.Comment;
import com.senla.kedaleanid.model.chat.Chat;
import com.senla.kedaleanid.model.chat.Message;
import com.senla.kedaleanid.model.transaction.Transaction;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, UserRole> role;
	public static volatile SetAttribute<User, Comment> comments;
	public static volatile SingularAttribute<User, Integer> rating;
	public static volatile ListAttribute<User, SecretCode> secretCode;
	public static volatile SingularAttribute<User, UserCreds> userCreds;
	public static volatile SetAttribute<User, Transaction> transactions;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, UserPhoto> photoUrl;
	public static volatile SetAttribute<User, Advertisement> advertisements;
	public static volatile SingularAttribute<User, String> phoneNumber;
	public static volatile SetAttribute<User, Chat> chats;
	public static volatile SetAttribute<User, Message> messages;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> email;

	public static final String LAST_NAME = "lastName";
	public static final String ROLE = "role";
	public static final String COMMENTS = "comments";
	public static final String RATING = "rating";
	public static final String SECRET_CODE = "secretCode";
	public static final String USER_CREDS = "userCreds";
	public static final String TRANSACTIONS = "transactions";
	public static final String FIRST_NAME = "firstName";
	public static final String PHOTO_URL = "photoUrl";
	public static final String ADVERTISEMENTS = "advertisements";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String CHATS = "chats";
	public static final String MESSAGES = "messages";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

