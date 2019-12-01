package com.senla.kedaleanid.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserCreds.class)
public abstract class UserCreds_ {

	public static volatile SingularAttribute<UserCreds, String> password;
	public static volatile SingularAttribute<UserCreds, String> login;
	public static volatile SingularAttribute<UserCreds, Integer> userId;
	public static volatile SingularAttribute<UserCreds, User> user;

	public static final String PASSWORD = "password";
	public static final String LOGIN = "login";
	public static final String USER_ID = "userId";
	public static final String USER = "user";

}

