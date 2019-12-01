package com.senla.kedaleanid.model.transaction;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Purpose.class)
public abstract class Purpose_ {

	public static volatile SingularAttribute<Purpose, String> name;
	public static volatile SingularAttribute<Purpose, Integer> id;
	public static volatile ListAttribute<Purpose, Transaction> transactions;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String TRANSACTIONS = "transactions";

}

