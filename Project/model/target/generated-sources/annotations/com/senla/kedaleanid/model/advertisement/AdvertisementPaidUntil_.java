package com.senla.kedaleanid.model.advertisement;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdvertisementPaidUntil.class)
public abstract class AdvertisementPaidUntil_ {

	public static volatile SingularAttribute<AdvertisementPaidUntil, Integer> adId;
	public static volatile SingularAttribute<AdvertisementPaidUntil, Timestamp> paidUntil;
	public static volatile SingularAttribute<AdvertisementPaidUntil, Advertisement> advertisement;

	public static final String AD_ID = "adId";
	public static final String PAID_UNTIL = "paidUntil";
	public static final String ADVERTISEMENT = "advertisement";

}

