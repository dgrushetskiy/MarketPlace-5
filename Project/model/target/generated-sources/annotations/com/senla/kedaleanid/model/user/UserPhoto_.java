package com.senla.kedaleanid.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserPhoto.class)
public abstract class UserPhoto_ {

	public static volatile SingularAttribute<UserPhoto, User> owner;
	public static volatile SingularAttribute<UserPhoto, String> photoUrl;
	public static volatile SingularAttribute<UserPhoto, Integer> userId;

	public static final String OWNER = "owner";
	public static final String PHOTO_URL = "photoUrl";
	public static final String USER_ID = "userId";

}

