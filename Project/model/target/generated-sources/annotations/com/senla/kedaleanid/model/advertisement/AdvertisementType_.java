package com.senla.kedaleanid.model.advertisement;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdvertisementType.class)
public abstract class AdvertisementType_ {

	public static volatile SingularAttribute<AdvertisementType, String> typeName;
	public static volatile SingularAttribute<AdvertisementType, Integer> typeId;
	public static volatile ListAttribute<AdvertisementType, Advertisement> advertisementList;

	public static final String TYPE_NAME = "typeName";
	public static final String TYPE_ID = "typeId";
	public static final String ADVERTISEMENT_LIST = "advertisementList";

}

