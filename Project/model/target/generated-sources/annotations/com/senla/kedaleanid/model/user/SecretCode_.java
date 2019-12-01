package com.senla.kedaleanid.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SecretCode.class)
public abstract class SecretCode_ {

	public static volatile SingularAttribute<SecretCode, Integer> code;
	public static volatile SingularAttribute<SecretCode, Integer> id;
	public static volatile SingularAttribute<SecretCode, Integer> userId;
	public static volatile SingularAttribute<SecretCode, User> user;

	public static final String CODE = "code";
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String USER = "user";

}

