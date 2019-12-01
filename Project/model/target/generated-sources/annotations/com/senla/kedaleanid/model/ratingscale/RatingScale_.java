package com.senla.kedaleanid.model.ratingscale;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RatingScale.class)
public abstract class RatingScale_ {

	public static volatile SingularAttribute<RatingScale, Integer> scaleId;
	public static volatile SingularAttribute<RatingScale, Integer> scaleThreshold;
	public static volatile SingularAttribute<RatingScale, String> scaleName;

	public static final String SCALE_ID = "scaleId";
	public static final String SCALE_THRESHOLD = "scaleThreshold";
	public static final String SCALE_NAME = "scaleName";

}

