package com.senla.kedaleanid.model.advertisement;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdvertisementCategory.class)
public abstract class AdvertisementCategory_ {

	public static volatile ListAttribute<AdvertisementCategory, Advertisement> advertisementList;
	public static volatile SingularAttribute<AdvertisementCategory, String> categoryName;
	public static volatile SingularAttribute<AdvertisementCategory, Integer> categoryId;

	public static final String ADVERTISEMENT_LIST = "advertisementList";
	public static final String CATEGORY_NAME = "categoryName";
	public static final String CATEGORY_ID = "categoryId";

}

