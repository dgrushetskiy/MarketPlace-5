package com.senla.kedaleanid.model.advertisement;

import com.senla.kedaleanid.model.user.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Advertisement.class)
public abstract class Advertisement_ {

	public static volatile SingularAttribute<Advertisement, User> owner;
	public static volatile SingularAttribute<Advertisement, String> adDescription;
	public static volatile SingularAttribute<Advertisement, String> adName;
	public static volatile SingularAttribute<Advertisement, AdvertisementType> adType;
	public static volatile SingularAttribute<Advertisement, Integer> adId;
	public static volatile SingularAttribute<Advertisement, AdvertisementPaidUntil> paidUntil;
	public static volatile ListAttribute<Advertisement, AdvertisementPhoto> adPhotos;
	public static volatile SingularAttribute<Advertisement, AdvertisementState> adState;
	public static volatile SingularAttribute<Advertisement, Timestamp> adDate;
	public static volatile ListAttribute<Advertisement, Comment> adComments;
	public static volatile SingularAttribute<Advertisement, Double> adPrice;
	public static volatile SingularAttribute<Advertisement, AdvertisementCategory> adCategory;

	public static final String OWNER = "owner";
	public static final String AD_DESCRIPTION = "adDescription";
	public static final String AD_NAME = "adName";
	public static final String AD_TYPE = "adType";
	public static final String AD_ID = "adId";
	public static final String PAID_UNTIL = "paidUntil";
	public static final String AD_PHOTOS = "adPhotos";
	public static final String AD_STATE = "adState";
	public static final String AD_DATE = "adDate";
	public static final String AD_COMMENTS = "adComments";
	public static final String AD_PRICE = "adPrice";
	public static final String AD_CATEGORY = "adCategory";

}

