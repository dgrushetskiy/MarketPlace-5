package com.senla.kedaleanid.model.advertisement;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdvertisementPhoto.class)
public abstract class AdvertisementPhoto_ {

	public static volatile SingularAttribute<AdvertisementPhoto, String> photoUrl;
	public static volatile SingularAttribute<AdvertisementPhoto, Integer> adId;
	public static volatile SingularAttribute<AdvertisementPhoto, Boolean> isMain;
	public static volatile SingularAttribute<AdvertisementPhoto, Advertisement> advertisement;
	public static volatile SingularAttribute<AdvertisementPhoto, Integer> id;

	public static final String PHOTO_URL = "photoUrl";
	public static final String AD_ID = "adId";
	public static final String IS_MAIN = "isMain";
	public static final String ADVERTISEMENT = "advertisement";
	public static final String ID = "id";

}

